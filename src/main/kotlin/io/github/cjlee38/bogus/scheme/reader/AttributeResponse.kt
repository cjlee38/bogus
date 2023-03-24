package io.github.cjlee38.bogus.scheme.reader

import io.github.cjlee38.bogus.config.AttributeConfiguration
import io.github.cjlee38.bogus.scheme.Attribute
import io.github.cjlee38.bogus.scheme.AttributeKey
import io.github.cjlee38.bogus.scheme.AttributeNullHandler
import io.github.cjlee38.bogus.scheme.Extra
import io.github.cjlee38.bogus.scheme.pattern.Pattern
import io.github.cjlee38.bogus.scheme.type.TypeInferrer

class AttributeResponse(
    val field: String,
    private val type: String,
    private val isNullable: String,
    private val key: String?,
    private val default: String?,
    private val extra: String?
) {
    fun toAttribute(typeInferrer: TypeInferrer, attributeConfiguration: AttributeConfiguration): Attribute {
        return Attribute(
            field = field,
            type = typeInferrer.inferType(type),
            key = AttributeKey(key),
            extra = Extra(extra),
            pattern = Pattern.of(attributeConfiguration.pattern),
            nullHandler = AttributeNullHandler(isNullable, attributeConfiguration.nullRatio, default)
        )
    }
}
