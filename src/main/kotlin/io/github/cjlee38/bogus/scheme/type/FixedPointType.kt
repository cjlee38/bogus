package io.github.cjlee38.bogus.scheme.type

import io.github.cjlee38.bogus.scheme.pattern.Pattern
import java.math.BigDecimal
import java.math.BigInteger

data class FixedPointType(
    val precision: String,
    val scale: String
) : DataType<BigDecimal> {
    override val cardinality: BigInteger
        get() = TODO("Not yet implemented")

    override fun generate(pattern: Pattern): BigDecimal {
        return BigDecimal.ONE
        // todo
    }
}

