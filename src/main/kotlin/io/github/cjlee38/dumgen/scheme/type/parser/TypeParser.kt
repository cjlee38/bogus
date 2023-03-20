package io.github.cjlee38.dumgen.scheme.type.parser

import io.github.cjlee38.dumgen.scheme.type.DType

interface TypeParser {
    fun parsable(notation: String): Boolean
    fun parse(notation: String): DType
}
