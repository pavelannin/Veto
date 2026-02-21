package io.github.pavelannin.veto.core.source.memory

import io.github.pavelannin.veto.core.source.FeatureFlagMutableSource
import io.github.pavelannin.veto.core.flag.FlowFeatureFlagSpec
import io.github.pavelannin.veto.core.flag.MutableFeatureFlagSpec
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.reflect.safeCast

public class VetoMemorySource(
    override val id: String = "Veto Memory Source",
    override val description: String? = "The source stores the values of the feature flags in volatile memory (RAM)",
) : FeatureFlagMutableSource {
    private val store by lazy { ObservableMutableMap<String, Any>() }

    override suspend fun <Value : Any> get(spec: MutableFeatureFlagSpec<Value>): Value? {
        return spec.valueType.safeCast(store[spec.id])
    }

    override fun <Value : Any> get(spec: FlowFeatureFlagSpec<Value>): Flow<Value?> {
        return store.observe(spec.id)
            .map { value -> spec.valueType.safeCast(value) }
    }

    override suspend fun <Value : Any> update(
        spec: MutableFeatureFlagSpec<Value>,
        block: suspend (Value?) -> Value,
    ) {
        val saved = spec.valueType.safeCast(store[spec.id])
        val new = block(saved)
        store[spec.id] = new
    }

    override suspend fun <Value : Any> update(
        spec: FlowFeatureFlagSpec<Value>,
        block: suspend (Value?) -> Value,
    ) {
        val saved = spec.valueType.safeCast(store[spec.id])
        val new = block(saved)
        store[spec.id] = new
    }

    override suspend fun <Value : Any> remove(spec: MutableFeatureFlagSpec<Value>) {
        store.remove(spec.id)
    }

    override suspend fun <Value : Any> remove(spec: FlowFeatureFlagSpec<Value>) {
        store.remove(spec.id)
    }

    override suspend fun clear() {
        store.clear()
    }
}
