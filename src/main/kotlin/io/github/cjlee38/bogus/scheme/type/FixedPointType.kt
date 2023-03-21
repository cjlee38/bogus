package io.github.cjlee38.bogus.scheme.type

data class FixedPointType(
    val precision: String,
    val scale: String
) : DType

