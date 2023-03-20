package io.github.cjlee38.bogus.scheme.type

data class StringType(
    val isVariable: Boolean,
    val length: Int
) : DType
