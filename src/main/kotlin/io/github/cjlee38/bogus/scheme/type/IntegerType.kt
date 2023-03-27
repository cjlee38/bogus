package io.github.cjlee38.bogus.scheme.type

import io.github.cjlee38.bogus.persistence.Sequence
import io.github.cjlee38.bogus.scheme.pattern.NumberPattern
import io.github.cjlee38.bogus.scheme.pattern.Pattern
import java.math.BigInteger
import java.util.concurrent.ThreadLocalRandom

data class IntegerType(
    val isUnsigned: Boolean,
    val min: Long,
    val max: Long,
) : DataType<Long> {
    override val cardinality: BigInteger
        get() = BigInteger.valueOf(max) - BigInteger.valueOf(min)

    constructor(isUnsigned: String, min: Long, max: Long) : this(isUnsigned == "unsigned", min, max)

    override fun generate(pattern: Pattern): Long {
        if (pattern === NumberPattern.SEQUENTIAL) {
            return Sequence.get(this)
        }
        val random = ThreadLocalRandom.current()
        return random.nextLong(min, max)
    }
}
