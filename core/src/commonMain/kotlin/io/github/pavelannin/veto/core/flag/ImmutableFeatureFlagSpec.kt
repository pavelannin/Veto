package io.github.pavelannin.veto.core.flag

import kotlin.reflect.KClass

public class ImmutableFeatureFlagValue<Value : Any>(
    public val spec: ImmutableFeatureFlagSpec<Value>,
    public val value: Value,
)

public data class ImmutableFeatureFlagSpec<Value : Any>(
    override val id: String,
    public val value: Value,
    override val valueType: KClass<Value>,
    override val description: String?,
) : FeatureFlagSpec<Value> {
    public class Builder<Value : Any> {
        public lateinit var id: String
        public lateinit var value: Value
        public lateinit var valueType: KClass<Value>
        public var description: String? = null

        public fun build(): ImmutableFeatureFlagSpec<Value> {
            return ImmutableFeatureFlagSpec(id, value, valueType, description)
        }
    }
}

public inline fun <reified Value : Any> ImmutableFeatureFlagSpec(
    id: String,
    value: Value,
    description: String? = null,
): ImmutableFeatureFlagSpec<Value> {
    return ImmutableFeatureFlagSpec(id, value, Value::class, description)
}

public inline fun <reified Value : Any> ImmutableFeatureFlagSpec.Builder<Value>.setValue(value: Value) {
    this.value = value
    this.valueType = Value::class
}
