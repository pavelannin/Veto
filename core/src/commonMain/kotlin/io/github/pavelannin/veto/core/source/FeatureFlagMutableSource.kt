package io.github.pavelannin.veto.core.source

import io.github.pavelannin.veto.core.flag.FlowFeatureFlagSpec
import io.github.pavelannin.veto.core.flag.MutableFeatureFlagSpec

public interface FeatureFlagMutableSource : FeatureFlagSource {
    public suspend fun <Value : Any> update(spec: MutableFeatureFlagSpec<Value>, block: suspend (Value?) -> Value)
    public suspend fun <Value : Any> update(spec: FlowFeatureFlagSpec<Value>, block: suspend (Value?) -> Value)
    public suspend fun <Value : Any> remove(spec: MutableFeatureFlagSpec<Value>)
    public suspend fun <Value : Any> remove(spec: FlowFeatureFlagSpec<Value>)
    public suspend fun clear()
}
