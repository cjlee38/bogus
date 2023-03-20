package io.github.cjlee38.bogus.scheme.type.parser

import io.github.cjlee38.bogus.scheme.type.DType
import io.github.cjlee38.bogus.scheme.type.IntegerType

class IntegerTypeParser : AbstractTypeParser() {
    override val knownTypes = listOf("bigint", "mediumint")

    override fun parse(notation: String): DType {
        val (name, zfill) = super.doSome(notation)

        if (name.equals("bigint", ignoreCase = true)) {
            val min = -1L shl 16
            val max = (1L shl 16) - 1
            return IntegerType(false, min, max)
        } else if (name.equals("mediumint", ignoreCase = true)) {
            return IntegerType(false, -1L shl 8, (1L shl 8) - 1)
        } else {
            throw IllegalArgumentException("temp : $notation")
        }
    }
}
