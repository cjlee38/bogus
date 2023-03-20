package io.github.cjlee38.dumgen.scheme

import io.github.cjlee38.dumgen.scheme.type.DType
import io.github.cjlee38.dumgen.scheme.type.TypeInferrer
import io.github.cjlee38.dumgen.scheme.type.parser.IntegerTypeParser
import io.github.cjlee38.dumgen.scheme.type.parser.StringTypeParser


data class Attribute(
    private val field: String,
    private val type: DType,
    private val isNullable: Boolean,
    private val key: String?,
    private val default: String?,
    private val extra: String?
) {
    constructor(
        field: String,
        type: String,
        isNullable: String?,
        key: String?,
        default: String?,
        extra: String?
    ) : this(field, TypeInferrer(listOf(IntegerTypeParser(), StringTypeParser())).inferType(type), isNullable.equals("YES", ignoreCase = true), key, default, extra)
}
