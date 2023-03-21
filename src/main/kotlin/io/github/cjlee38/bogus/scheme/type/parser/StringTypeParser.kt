package io.github.cjlee38.bogus.scheme.type.parser

import io.github.cjlee38.bogus.scheme.type.StringType

class StringTypeParser : AbstractTypeParser() {
    override val knownTypes: List<String> = listOf("varchar", "char")

    override fun parse(notation: String): StringType {
        val (type, length) = destruct(notation)
        val isVariable = type == "varchar"
        return StringType(isVariable, length.toInt())
    }
}
