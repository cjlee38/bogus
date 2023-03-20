package io.github.cjlee38.dumgen.scheme.type

data class IntegerType(
    val isUnsigned: Boolean,
    val min: Long,
    val max: Long
) : DType
