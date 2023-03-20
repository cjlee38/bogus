package io.github.cjlee38.bogus.generator

import io.github.cjlee38.bogus.scheme.Attribute
import io.github.cjlee38.bogus.scheme.Relation
import io.github.cjlee38.bogus.scheme.type.IntegerType
import io.github.cjlee38.bogus.scheme.type.StringType
import org.springframework.stereotype.Component

@Component
class TableGenerator(
    private val stringTypeGenerator: StringTypeGenerator,
    private val integerTypeGenerator: IntegerTypeGenerator,
) {

    fun generate(relation: Relation, count: Int): Table {
        return (0..count).map {
            relation.attributes
                .map { getValue(it) }
                .let(::Tuple)
        }.let(::Table)
    }

    private fun getValue(attribute: Attribute): Any? {
        if (attribute.type is StringType) {
            return stringTypeGenerator.generate(attribute.type)
        } else if (attribute.type is IntegerType) {
            return integerTypeGenerator.generate(attribute.type)
        }
        return null
    }
}
