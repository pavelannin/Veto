package io.github.pavelannin.veto.core.scope

import io.github.pavelannin.veto.core.Veto
import io.github.pavelannin.veto.core.flag.FeatureFlagSpec
import io.github.pavelannin.veto.core.flag.FlowFeatureFlagSpec
import io.github.pavelannin.veto.core.flag.FlowFeatureFlagValue
import io.github.pavelannin.veto.core.flag.ImmutableFeatureFlagSpec
import io.github.pavelannin.veto.core.flag.ImmutableFeatureFlagValue
import io.github.pavelannin.veto.core.flag.MutableFeatureFlagSpec
import io.github.pavelannin.veto.core.flag.MutableFeatureFlagValue
import kotlin.properties.ReadOnlyProperty

public class VetoFeatureFlagScope(
    public val veto: Veto,
) : FeatureFlagScope {
    private val specs = mutableSetOf<FeatureFlagSpec<*>>()

    override fun allFeatureFlagSpecs(): Set<FeatureFlagSpec<*>> {
        return specs
    }

    override fun <Value : Any> immutable(
        spec: ImmutableFeatureFlagSpec<Value>,
    ): ReadOnlyProperty<FeatureFlagScope, ImmutableFeatureFlagValue<Value>> {
        specs += spec
        return ReadOnlyProperty { _, _ -> ImmutableFeatureFlagValue(spec, veto[spec]) }
    }

    override fun <Value : Any> immutable(
        block: ImmutableFeatureFlagSpec.Builder<Value>.() -> Unit,
    ): ReadOnlyProperty<FeatureFlagScope, ImmutableFeatureFlagValue<Value>> {
        val spec = ImmutableFeatureFlagSpec.Builder<Value>().apply(block).build()
        specs += spec
        return ReadOnlyProperty { _, _ -> ImmutableFeatureFlagValue(spec, veto[spec]) }
    }

    override fun <Value : Any> mutable(
        spec: MutableFeatureFlagSpec<Value>,
    ): ReadOnlyProperty<FeatureFlagScope, MutableFeatureFlagValue<Value>> {
        specs += spec
        return ReadOnlyProperty { _, _ -> MutableFeatureFlagValue(spec) { veto[spec] } }
    }

    override fun <Value : Any> mutable(
        block: MutableFeatureFlagSpec.Builder<Value>.() -> Unit,
    ): ReadOnlyProperty<FeatureFlagScope, MutableFeatureFlagValue<Value>> {
        val spec = MutableFeatureFlagSpec.Builder<Value>().apply(block).build()
        specs += spec
        return ReadOnlyProperty { _, _ -> MutableFeatureFlagValue(spec) { veto[spec] } }
    }

    override fun <Value : Any> flow(
        spec: FlowFeatureFlagSpec<Value>,
    ): ReadOnlyProperty<FeatureFlagScope, FlowFeatureFlagValue<Value>> {
        specs += spec
        return ReadOnlyProperty { _, _ -> FlowFeatureFlagValue(spec, veto[spec]) }
    }

    override fun <Value : Any> flow(
        block: FlowFeatureFlagSpec.Builder<Value>.() -> Unit,
    ): ReadOnlyProperty<FeatureFlagScope, FlowFeatureFlagValue<Value>> {
        val spec = FlowFeatureFlagSpec.Builder<Value>().apply(block).build()
        specs += spec
        return ReadOnlyProperty { _, _ -> FlowFeatureFlagValue(spec, veto[spec]) }
    }
}