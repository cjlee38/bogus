package io.github.cjlee38.bogus.scheme.type

data class IntegerType(
    val isUnsigned: Boolean,
    val min: Long,
    val max: Long
) : DType
