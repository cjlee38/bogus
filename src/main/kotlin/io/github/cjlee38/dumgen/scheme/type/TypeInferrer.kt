package io.github.cjlee38.dumgen.scheme.type

import io.github.cjlee38.dumgen.scheme.type.parser.TypeParser
import org.springframework.stereotype.Component

@Component
class TypeInferrer(
    private val parsers: List<TypeParser>
) {
    fun inferType(notation: String): DType {
        return parsers.firstOrNull { it.parsable(notation) }
            ?.parse(notation)
            ?: throw IllegalArgumentException("todo")
    }
}
