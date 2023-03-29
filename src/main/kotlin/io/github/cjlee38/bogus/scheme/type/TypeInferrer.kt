package io.github.cjlee38.bogus.scheme.type

import io.github.cjlee38.bogus.scheme.type.parser.TypeParser
import org.springframework.stereotype.Component

@Component
class TypeInferrer(
    private val parsers: List<TypeParser>
) {
    fun inferType(notation: String): DataType<Any> {
        return parsers.find { it.parsable(notation) }
            ?.parse(notation)
            ?: throw IllegalArgumentException("infer type error : $notation")
    }
}
