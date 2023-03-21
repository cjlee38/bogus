package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.generator.Column
import io.github.cjlee38.bogus.persistence.Storage
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
    var reference: Reference? = null
        internal set
    val isPrimary: Boolean
        get() = key == "PRI"

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
        isNullable == "YES",
        key,
        default,
        extra
    )

    fun generateColumn(count: Int): Column {
        if (reference != null) {
//            val to = reference!!.to
//            to.
        }
        if (isNullable) {
            // todo
        }

        return Column(this, (0 until count).map { type.generateRandom() })
    }
}
