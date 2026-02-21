package io.github.pavelannin.veto.core.source

import io.github.pavelannin.veto.core.flag.FlowFeatureFlagSpec
import io.github.pavelannin.veto.core.flag.MutableFeatureFlagSpec
import kotlinx.coroutines.flow.Flow

public interface FeatureFlagSource {
    public val id: String
    public val description: String?

    public suspend operator fun <Value : Any> get(spec: MutableFeatureFlagSpec<Value>): Value?
    public operator fun <Value : Any> get(spec: FlowFeatureFlagSpec<Value>): Flow<Value?>
}
