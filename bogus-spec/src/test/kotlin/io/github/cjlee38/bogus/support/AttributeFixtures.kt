package io.github.cjlee38.bogus.support

import io.github.cjlee38.bogus.scheme.Attribute
import io.github.cjlee38.bogus.scheme.Constraint
import io.github.cjlee38.bogus.scheme.Constraints
import io.github.cjlee38.bogus.scheme.pattern.Pattern
import io.github.cjlee38.bogus.scheme.type.DataType


fun createAttribute(
    field: String = "fixture-id",
    type: DataType<Any> = createIntegerType(),
    pattern: Pattern = Pattern.RANDOM,
    constraints: Constraints = Constraints(emptyList()),
    isPrimary: Boolean = false,
): Attribute {
    val attribute = Attribute(field, type, pattern, constraints, isPrimary)
    attribute.relation = createRelation(attribute)
    return attribute
}

fun createConstraints(
    vararg constraints: Constraint
): Constraints {
    return Constraints(constraints.toList())
}
