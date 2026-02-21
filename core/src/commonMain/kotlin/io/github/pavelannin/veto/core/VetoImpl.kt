package io.github.pavelannin.veto.core

import io.github.pavelannin.veto.core.flag.FlowFeatureFlagSpec
import io.github.pavelannin.veto.core.flag.ImmutableFeatureFlagSpec
import io.github.pavelannin.veto.core.flag.MutableFeatureFlagSpec
import io.github.pavelannin.veto.core.interceptor.FeatureFlagInterceptor
import io.github.pavelannin.veto.core.source.FeatureFlagSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.stateIn

public fun Veto(
    sources: Set<FeatureFlagSource> = emptySet(),
    interceptors: Set<FeatureFlagInterceptor> = emptySet(),
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
): Veto {
    return Impl(
        sources = sources,
        interceptors = interceptors,
        dispatcher = dispatcher,
    )
}

private class Impl(
    sources: Set<FeatureFlagSource>,
    interceptors: Set<FeatureFlagInterceptor>,
    dispatcher: CoroutineDispatcher,
) : Veto {
    private val sources = mutableSetOf<FeatureFlagSource>()
    private val interceptors = mutableSetOf<FeatureFlagInterceptor>()
    private val coroutineScope = CoroutineScope(dispatcher)

    init {
        if (sources.isNotEmpty()) {
            this.sources += sources
        }
        if (interceptors.isNotEmpty()) {
            this.interceptors += interceptors
        }
    }

    override fun <Value : Any> get(spec: ImmutableFeatureFlagSpec<Value>): Value {
        val resultValue = interceptors.fold(spec.value) { value, interceptor ->
            context(interceptor) { interceptor.intercept(spec, value) }
        }
        return resultValue
    }

    override suspend fun <Value : Any> get(spec: MutableFeatureFlagSpec<Value>): Value {
        var resultValue = spec.defaultValue
        var valueSource: FeatureFlagSource? = null

        for (source in sources) {
            val value = source[spec]
            if (value != null) {
                resultValue = value
                valueSource = source
                break
            }
        }

        resultValue = interceptors.fold(resultValue) { value, interceptor ->
            context(interceptor) { interceptor.intercept(spec, valueSource, value) }
        }
        return resultValue
    }

    override fun <Value : Any> get(spec: FlowFeatureFlagSpec<Value>): StateFlow<Value> {
        return flow {
            val flows = sources.map { source ->
                source[spec]
                    .map { value -> source to value }
                    .onEmpty { emit(source to null) }
            }
            combine(flows) { values ->
                for ((source, value) in values) {
                    if (value != null) {
                        return@combine source to value
                    }
                }
                return@combine null to spec.defaultValue
            }.collect { (source, resultValue) ->
                val resultValue = interceptors.fold(resultValue) { value, interceptor ->
                    context(interceptor) { interceptor.intercept(spec, source, value) }
                }
                emit(resultValue)
            }
        }.stateIn(coroutineScope, spec.startedStrategy, spec.defaultValue)
    }

    override fun addSource(source: FeatureFlagSource) {
        sources += source
    }

    override fun removeSource(source: FeatureFlagSource) {
        sources -= source
    }

    override fun allSources(): Set<FeatureFlagSource> {
        return sources.toSet()
    }

    override fun addInterceptor(interceptor: FeatureFlagInterceptor) {
        interceptors += interceptor
    }

    override fun removeInterceptor(interceptor: FeatureFlagInterceptor) {
        interceptors -= interceptor
    }

    override fun allInterceptors(): Set<FeatureFlagInterceptor> {
        return interceptors.toSet()
    }
}
