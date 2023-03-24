package io.github.cjlee38.bogus.scheme.type

import java.util.concurrent.ThreadLocalRandom

data class FloatingPointType(
    val min: Double,
    val max: Double
) : DataType {
    override fun generateRandom(): Any {
        val threadLocalRandom = ThreadLocalRandom.current()
        return threadLocalRandom.nextDouble(min, max)
    }
}
