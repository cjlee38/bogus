package io.github.cjlee38.bogus.scheme.type.parser

import io.github.cjlee38.bogus.scheme.type.DType

interface TypeParser {
    fun parsable(notation: String): Boolean
    fun parse(notation: String): DType
}
