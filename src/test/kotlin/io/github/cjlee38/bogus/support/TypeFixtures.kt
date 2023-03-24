package io.github.cjlee38.bogus.support

import io.github.cjlee38.bogus.scheme.type.DType
import io.github.cjlee38.bogus.scheme.type.IntegerType
import io.github.cjlee38.bogus.scheme.type.StringType

/**
 * default = bigint
 */
fun createIntegerType(
    isUnsigned: Boolean = false,
    min: Long = Long.MIN_VALUE,
    max: Long = Long.MAX_VALUE
): DType {
    return IntegerType(isUnsigned, min, max)
}

/**
 * default = varchar(255)
 */
fun createStringType(
    isVariable: Boolean = true,
    length: Int = 255
): DType {
    return StringType(isVariable, length)
}
