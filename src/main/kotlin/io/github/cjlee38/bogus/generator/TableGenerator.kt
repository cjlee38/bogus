package io.github.cjlee38.bogus.generator

import io.github.cjlee38.bogus.scheme.Relation
import org.springframework.stereotype.Component

@Component
class TableGenerator {
    fun generate(relation: Relation, count: Int): Table {
        return (0..count).map {
            relation.attributes
                .map { it.generateRandom() }
                .let(::Tuple)
        }.let(::Table)
    }
}
