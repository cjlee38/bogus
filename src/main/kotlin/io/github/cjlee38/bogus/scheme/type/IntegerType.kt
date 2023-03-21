package io.github.cjlee38.bogus.scheme.type

import java.util.concurrent.ThreadLocalRandom

data class IntegerType(
    val isUnsigned: Boolean,
    val min: Long,
    val max: Long
) : DType {
    constructor(isUnsigned: String, min: Long, max: Long) : this(isUnsigned == "unsigned", min, max)

    override fun generateRandom(): Any {
        val random = ThreadLocalRandom.current()
        return random.nextLong(min, max)
    }
}
