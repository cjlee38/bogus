package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.scheme.type.DType
import io.github.cjlee38.bogus.scheme.type.TypeInferrer
import io.github.cjlee38.bogus.scheme.type.parser.IntegerTypeParser
import io.github.cjlee38.bogus.scheme.type.parser.StringTypeParser


data class Attribute(
    val field: String,
    val type: DType,
    val isNullable: Boolean,
    val key: String?,
    val default: String?,
    val extra: String?
) {
    constructor(
        field: String,
        type: String,
        isNullable: String?,
        key: String?,
        default: String?,
        extra: String?
    ) : this(
        field,
        TypeInferrer(listOf(IntegerTypeParser(), StringTypeParser())).inferType(type),
        isNullable.equals("YES", ignoreCase = true),
        key,
        default,
        extra
    )

    fun generateRandom(): Any? {
        if (isNullable) {
            // todo
        }
        return type.generateRandom()
    }
}
