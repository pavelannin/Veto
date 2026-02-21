package io.github.pavelannin.veto.core.interceptor.logger

import io.github.pavelannin.veto.core.Veto
import io.github.pavelannin.veto.core.interceptor.FeatureFlagInterceptor
import io.github.pavelannin.veto.core.logger.VetoLogger
import io.github.pavelannin.veto.core.source.FeatureFlagSource
import io.github.pavelannin.veto.core.flag.FlowFeatureFlagSpec
import io.github.pavelannin.veto.core.flag.ImmutableFeatureFlagSpec
import io.github.pavelannin.veto.core.flag.MutableFeatureFlagSpec

public class VetoLoggerInterceptor(
    private val logger: VetoLogger = VetoLogger(),
) : FeatureFlagInterceptor {
    override val id: String = "Veto Logger Interceptor"
    override val description: String = "The logger logs the values of the feature flags at the time they are accessed."

    context(veto: Veto)
    override fun <Value : Any> intercept(
        spec: ImmutableFeatureFlagSpec<Value>,
        value: Value,
    ): Value {
        logger.info("Get value '$value' by immutable spec '${spec.id}'")
        return value
    }

    context(veto: Veto)
    override suspend fun <Value : Any> intercept(
        spec: MutableFeatureFlagSpec<Value>,
        source: FeatureFlagSource?,
        value: Value,
    ): Value {
        logger.info("Get value '$value' by mutable spec '${spec.id}' from source '${source?.id ?: "default value"}'")
        return value
    }

    context(veto: Veto)
    override suspend fun <Value : Any> intercept(
        spec: FlowFeatureFlagSpec<Value>,
        source: FeatureFlagSource?,
        value: Value,
    ): Value {
        logger.info("Get value '$value' by flow spec '${spec.id}' from source '${source?.id ?: "default value"}'")
        return value
    }
}
