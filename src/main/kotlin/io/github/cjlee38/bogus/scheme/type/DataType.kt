package io.github.cjlee38.bogus.scheme.type

import io.github.cjlee38.bogus.scheme.pattern.Pattern
import java.math.BigInteger

interface DataType<T> {
    val cardinality: BigInteger
    fun generate(pattern: Pattern): T
}
