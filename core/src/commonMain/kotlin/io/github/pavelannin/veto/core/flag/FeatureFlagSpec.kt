package io.github.pavelannin.veto.core.flag

import kotlin.reflect.KClass

public sealed interface FeatureFlagSpec<Value : Any> {
    public val id: String
    public val description: String?
    public val valueType: KClass<Value>
}
