package io.github.pavelannin.veto.core.source.memory

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

internal class ObservableMutableMap<Key, Value> {
    private val sharedFlow = MutableStateFlow<Map<Key, Value>>(emptyMap())

    operator fun get(key: Key): Value? {
        return sharedFlow.value[key]
    }

    fun observe(key: Key): Flow<Value?> {
        return sharedFlow
            .map { map -> map[key] }
            .distinctUntilChanged()
    }

    operator fun set(key: Key, value: Value) {
        sharedFlow.update { map -> map.plus(key to value) }
    }

    fun remove(key: Key) {
        sharedFlow.update { map -> map.minus(key) }
    }

    fun clear() {
        sharedFlow.update { _ -> emptyMap() }
    }
}
