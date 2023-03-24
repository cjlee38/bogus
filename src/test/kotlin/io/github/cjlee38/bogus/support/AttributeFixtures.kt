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
    key: AttributeKey = AttributeKey.NONE,
    extra: Extra = Extra(true),
    pattern: Pattern = Pattern.RANDOM,
    nullHandler: AttributeNullHandler = AttributeNullHandler.NOT_NULL
): Attribute {
    val attribute = Attribute(field, type, key, extra, pattern, nullHandler)
    attribute.relation = createRelation(attribute)
    return attribute
}
