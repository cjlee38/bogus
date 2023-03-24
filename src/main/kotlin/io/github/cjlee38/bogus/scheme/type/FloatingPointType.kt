package io.github.cjlee38.bogus.scheme.type

import io.github.cjlee38.bogus.scheme.pattern.Pattern
import java.util.concurrent.ThreadLocalRandom

data class FloatingPointType(
    val min: Double,
    val max: Double
) : DataType<Double> {
    override fun generate(pattern: Pattern): Double {
        val threadLocalRandom = ThreadLocalRandom.current()
        return threadLocalRandom.nextDouble(min, max)
    }
}
