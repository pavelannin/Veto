package io.github.pavelannin.veto.core.scope

import io.github.pavelannin.veto.core.flag.FeatureFlagSpec
import io.github.pavelannin.veto.core.flag.FlowFeatureFlagSpec
import io.github.pavelannin.veto.core.flag.FlowFeatureFlagValue
import io.github.pavelannin.veto.core.flag.ImmutableFeatureFlagSpec
import io.github.pavelannin.veto.core.flag.ImmutableFeatureFlagValue
import io.github.pavelannin.veto.core.flag.MutableFeatureFlagSpec
import io.github.pavelannin.veto.core.flag.MutableFeatureFlagValue
import kotlin.properties.ReadOnlyProperty

public interface FeatureFlagScope {
    public fun allFeatureFlagSpecs(): Set<FeatureFlagSpec<*>>

    public fun <Value : Any> immutable(
        spec: ImmutableFeatureFlagSpec<Value>,
    ): ReadOnlyProperty<FeatureFlagScope, ImmutableFeatureFlagValue<Value>>

    public fun <Value : Any> immutable(
        block: ImmutableFeatureFlagSpec.Builder<Value>.() -> Unit
    ): ReadOnlyProperty<FeatureFlagScope, ImmutableFeatureFlagValue<Value>>

    public fun <Value : Any> mutable(
        spec: MutableFeatureFlagSpec<Value>,
    ): ReadOnlyProperty<FeatureFlagScope, MutableFeatureFlagValue<Value>>

    public fun <Value : Any> mutable(
        block: MutableFeatureFlagSpec.Builder<Value>.() -> Unit,
    ): ReadOnlyProperty<FeatureFlagScope, MutableFeatureFlagValue<Value>>

    public fun <Value : Any> flow(
        spec: FlowFeatureFlagSpec<Value>,
    ): ReadOnlyProperty<FeatureFlagScope, FlowFeatureFlagValue<Value>>

    public fun <Value : Any> flow(
        block: FlowFeatureFlagSpec.Builder<Value>.() -> Unit,
    ): ReadOnlyProperty<FeatureFlagScope, FlowFeatureFlagValue<Value>>
}
