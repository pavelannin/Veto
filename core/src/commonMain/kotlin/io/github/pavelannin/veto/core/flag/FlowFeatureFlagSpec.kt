package io.github.pavelannin.veto.core.flag

import io.github.pavelannin.veto.core.annotation.FlowFeatureFlagMomentValue
import kotlinx.coroutines.ExperimentalForInheritanceCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlin.reflect.KClass

@OptIn(ExperimentalForInheritanceCoroutinesApi::class)
public class FlowFeatureFlagValue<Value : Any>(
    public val spec: FlowFeatureFlagSpec<Value>,
    public val flow: StateFlow<Value>,
) : StateFlow<Value> by flow {
    @FlowFeatureFlagMomentValue
    override val value: Value get() = flow.value
}

public data class FlowFeatureFlagSpec<Value : Any>(
    override val id: String,
    public val defaultValue: Value,
    override val valueType: KClass<Value>,
    override val description: String?,
    public val startedStrategy: SharingStarted,
) : FeatureFlagSpec<Value> {

    public class Builder<Value : Any> {
        public lateinit var id: String
        public lateinit var defaultValue: Value
        public lateinit var valueType: KClass<Value>
        public var description: String? = null
        public var startedStrategy: SharingStarted = SharingStarted.Lazily

        public fun build(): FlowFeatureFlagSpec<Value> {
            return FlowFeatureFlagSpec(id, defaultValue, valueType, description, startedStrategy)
        }
    }
}

public inline fun <reified Value : Any> FlowFeatureFlagSpec(
    id: String,
    defaultValue: Value,
    description: String? = null,
    startedStrategy: SharingStarted = SharingStarted.Lazily,
): FlowFeatureFlagSpec<Value> {
    return FlowFeatureFlagSpec(id, defaultValue, Value::class, description, startedStrategy)
}

public inline fun <reified Value : Any> FlowFeatureFlagSpec.Builder<Value>.setValue(value: Value) {
    this.defaultValue = value
    this.valueType = Value::class
}
