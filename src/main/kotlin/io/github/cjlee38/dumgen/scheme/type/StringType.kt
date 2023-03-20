package io.github.cjlee38.dumgen.scheme.type

data class StringType(
    private val isVariable: Boolean,
    private val length: Int
) : DType
