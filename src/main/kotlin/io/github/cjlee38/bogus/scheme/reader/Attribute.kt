package io.github.cjlee38.bogus.scheme.reader

import io.github.cjlee38.bogus.scheme.Attribute
import io.github.cjlee38.bogus.scheme.AttributeKey
import io.github.cjlee38.bogus.scheme.Extra
import io.github.cjlee38.bogus.scheme.type.TypeInferrer

class Attribute(
    private val field: String,
    private val type: String,
    private val isNullable: String,
    private val key: String?,
    private val default: String?,
    private val extra: String?
) {
    fun toAttribute(typeInferrer: TypeInferrer): Attribute {
        return Attribute(
            field = field,
            type = typeInferrer.inferType(type),
            isNullable = isNullable == "yes",
            key = AttributeKey(key),
            default = default,
            extra = Extra(extra)
        )
    }
}
