package io.github.cjlee38.bogus.scheme.type

import io.github.cjlee38.bogus.scheme.pattern.Pattern

interface DataType<out T> {
    val cardinality: Long
    fun generate(pattern: Pattern): T
}
