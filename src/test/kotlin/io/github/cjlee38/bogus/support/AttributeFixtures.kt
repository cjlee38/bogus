package io.github.cjlee38.bogus.support

import io.github.cjlee38.bogus.scheme.Attribute
import io.github.cjlee38.bogus.scheme.AttributeKey
import io.github.cjlee38.bogus.scheme.AttributeNullHandler
import io.github.cjlee38.bogus.scheme.Extra
import io.github.cjlee38.bogus.scheme.pattern.Pattern
import io.github.cjlee38.bogus.scheme.type.DataType


fun createAttribute(
    field: String = "fixture-id",
    type: DataType<*> = createIntegerType(),
    isNullable: Boolean = false,
    key: AttributeKey = AttributeKey.NONE,
    default: String? = "",
    extra: Extra = Extra(true)
): Attribute {
    val attribute = Attribute(field, type, key, extra, Pattern.RANDOM, AttributeNullHandler(isNullable, 0.01, default)) // temp
    attribute.relation = createRelation(attribute)
    return attribute
}
