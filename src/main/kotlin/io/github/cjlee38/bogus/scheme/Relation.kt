package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.generator.Table

data class Relation(
    val name: String,
    val attributes: List<Attribute>
) {
    init {
        attributes.forEach { it.relation = this }
    }

    val fields: List<String>
        get() = attributes.map { it.field }

    fun generateTable(count: Int): Table {
        return Table(
            relation = this,
            rowCount = count,
            columns = attributes.map { attribute -> attribute.generateColumn(count) }
        )
    }
}
