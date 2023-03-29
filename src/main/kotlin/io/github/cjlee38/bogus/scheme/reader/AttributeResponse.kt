package io.github.cjlee38.bogus.scheme.reader

import io.github.cjlee38.bogus.config.AttributeConfiguration
import io.github.cjlee38.bogus.scheme.Attribute
import io.github.cjlee38.bogus.scheme.Constraint
import io.github.cjlee38.bogus.scheme.Constraints
import io.github.cjlee38.bogus.scheme.DefaultConstraint
import io.github.cjlee38.bogus.scheme.NullableConstraint
import io.github.cjlee38.bogus.scheme.AutoIncrementConstraint
import io.github.cjlee38.bogus.scheme.UniqueConstraint
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
    fun toAttribute(
        typeInferrer: TypeInferrer,
        attributeConfiguration: AttributeConfiguration,
    ): Attribute {
        return Attribute(
            field = field,
            type = typeInferrer.inferType(type),
            pattern = Pattern.of(attributeConfiguration.pattern),
            constraints = createConstraints(isNullable, attributeConfiguration.nullRatio, default, extra, key),
            isPrimary = key == "pri"
        )
    }

    private fun createConstraints(isNullable: String, nullRatio: Double?, default: String?, extra: String?, key: String?): Constraints {
        val values = mutableListOf<Constraint>()
        if (isNullable == "yes") values.add(NullableConstraint(nullRatio!!))
        if (default != null) values.add(DefaultConstraint())
        if (key == "pri" && extra == "auto_increment") values.add(AutoIncrementConstraint())
        if (key == "pri" || key == "uni") values.add(UniqueConstraint(null))
        return Constraints(values)
    }
}
