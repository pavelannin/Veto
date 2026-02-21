package io.github.pavelannin.veto.core.flag

import kotlin.reflect.KClass

public class MutableFeatureFlagValue<Value : Any>(
    public val spec: MutableFeatureFlagSpec<Value>,
    public val value: suspend () -> Value,
) : suspend () -> Value by value

public data class MutableFeatureFlagSpec<Value : Any>(
    override val id: String,
    public val defaultValue: Value,
    override val valueType: KClass<Value>,
    override val description: String?,
) : FeatureFlagSpec<Value> {
    public class Builder<Value : Any> {
        public lateinit var id: String
        public lateinit var defaultValue: Value
        public lateinit var valueType: KClass<Value>
        public var description: String? = null

        public fun build(): MutableFeatureFlagSpec<Value> {
            return MutableFeatureFlagSpec(id, defaultValue, valueType, description)
        }
    }
}

public inline fun <reified Value : Any> MutableFeatureFlagSpec(
    id: String,
    defaultValue: Value,
    description: String? = null,
): MutableFeatureFlagSpec<Value> {
    return MutableFeatureFlagSpec(id, defaultValue, Value::class, description)
}

public inline fun <reified Value : Any> MutableFeatureFlagSpec.Builder<Value>.setValue(value: Value) {
    this.defaultValue = value
    this.valueType = Value::class
}
