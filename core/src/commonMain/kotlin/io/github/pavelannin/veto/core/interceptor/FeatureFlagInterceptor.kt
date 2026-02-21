package io.github.pavelannin.veto.core.interceptor

import io.github.pavelannin.veto.core.Veto
import io.github.pavelannin.veto.core.source.FeatureFlagSource
import io.github.pavelannin.veto.core.flag.FlowFeatureFlagSpec
import io.github.pavelannin.veto.core.flag.ImmutableFeatureFlagSpec
import io.github.pavelannin.veto.core.flag.MutableFeatureFlagSpec

public interface FeatureFlagInterceptor {
    public val id: String
    public val description: String?

    context(veto: Veto)
    public fun <Value : Any> intercept(
        spec: ImmutableFeatureFlagSpec<Value>,
        value: Value,
    ): Value

    context(veto: Veto)
    public suspend fun <Value : Any> intercept(
        spec: MutableFeatureFlagSpec<Value>,
        source: FeatureFlagSource?,
        value: Value,
    ): Value

    context(veto: Veto)
    public suspend fun <Value : Any> intercept(
        spec: FlowFeatureFlagSpec<Value>,
        source: FeatureFlagSource?,
        value: Value,
    ): Value
}
