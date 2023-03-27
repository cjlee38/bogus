package io.github.cjlee38.bogus.generator

import io.github.cjlee38.bogus.scheme.type.Default
import io.github.cjlee38.bogus.scheme.type.Null

data class Tuple(val values: List<Any?>) {
    operator fun get(index: Int): Any? {
        return values[index]
    }

    fun format(): List<String?> {
        return values.map {
            when (it) {
                is Null -> null
                is Default -> it.toString()
                is Number -> it.toString()
                else -> "'$it'"
            }
        }
    }
}
