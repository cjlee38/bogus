package io.github.cjlee38.bogus.scheme.type

import org.apache.commons.lang3.RandomStringUtils

data class StringType(
    val isVariable: Boolean,
    val length: Int
) : DataType {
    override fun generateRandom(): Any {
        if (isVariable) {
            return RandomStringUtils.randomAlphanumeric(0, length)
        }
        return RandomStringUtils.randomAlphanumeric(length)
    }
}
