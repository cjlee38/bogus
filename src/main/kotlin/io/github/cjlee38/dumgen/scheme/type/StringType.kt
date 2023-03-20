package io.github.cjlee38.dumgen.scheme.type

data class StringType(
    val isVariable: Boolean,
    val length: Int
) : DType
