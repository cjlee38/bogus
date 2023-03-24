package io.github.cjlee38.bogus.scheme.type

import io.github.cjlee38.bogus.scheme.pattern.Pattern
import java.math.BigDecimal

data class FixedPointType(
    val precision: String,
    val scale: String
) : DataType<BigDecimal> {
    override fun generate(pattern: Pattern): BigDecimal {
        return BigDecimal.ONE
        // todo
    }
}

