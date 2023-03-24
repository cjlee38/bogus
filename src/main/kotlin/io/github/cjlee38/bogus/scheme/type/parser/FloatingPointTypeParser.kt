package io.github.cjlee38.bogus.scheme.type.parser

import io.github.cjlee38.bogus.scheme.type.DataType
import io.github.cjlee38.bogus.scheme.type.FloatingPointType
import org.springframework.stereotype.Component

@Component
class FloatingPointTypeParser : AbstractTypeParser() {
    override val knownTypes = listOf("float", "double")

    override fun parse(notation: String): DataType {
        val (type, _, _) = super.destruct(notation)
        val (min, max) =
            if (type == "float") Float.MIN_VALUE.toDouble() to Float.MAX_VALUE.toDouble()
            else Double.MIN_VALUE to Double.MAX_VALUE
        return FloatingPointType(min, max)
    }
}
