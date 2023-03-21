package io.github.cjlee38.bogus.scheme.type

import java.math.BigDecimal

data class FixedPointType(
    val precision: String,
    val scale: String
) : DType {
    override fun generateRandom(): Any {
        return BigDecimal.ONE
        // todo
    }
}

