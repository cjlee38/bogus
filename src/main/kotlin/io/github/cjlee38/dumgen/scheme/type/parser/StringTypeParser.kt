package io.github.cjlee38.dumgen.scheme.type.parser

import io.github.cjlee38.dumgen.scheme.type.StringType

class StringTypeParser : AbstractTypeParser() {
    override val knownTypes: List<String> = listOf("varchar", "char")

    override fun parse(notation: String): StringType {
        val (name, length) = doSome(notation)
        if (name.equals("varchar", ignoreCase = true)) {
            return StringType(isVariable = true, length.toInt())
        }
        throw IllegalArgumentException()
    }
}
