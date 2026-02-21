package io.github.pavelannin.veto.core

import io.github.pavelannin.veto.core.flag.FlowFeatureFlagSpec
import io.github.pavelannin.veto.core.flag.ImmutableFeatureFlagSpec
import io.github.pavelannin.veto.core.flag.MutableFeatureFlagSpec
import io.github.pavelannin.veto.core.interceptor.FeatureFlagInterceptor
import io.github.pavelannin.veto.core.source.FeatureFlagSource
import kotlinx.coroutines.flow.StateFlow

public interface Veto {
    public operator fun <Value : Any> get(spec: ImmutableFeatureFlagSpec<Value>): Value
    public suspend operator fun <Value : Any> get(spec: MutableFeatureFlagSpec<Value>): Value
    public operator fun <Value : Any> get(spec: FlowFeatureFlagSpec<Value>): StateFlow<Value>

    public fun addSource(source: FeatureFlagSource)
    public fun removeSource(source: FeatureFlagSource)
    public fun allSources(): Set<FeatureFlagSource>

    public fun addInterceptor(interceptor: FeatureFlagInterceptor)
    public fun removeInterceptor(interceptor: FeatureFlagInterceptor)
    public fun allInterceptors(): Set<FeatureFlagInterceptor>
}
