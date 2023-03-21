package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.generator.Column
import io.github.cjlee38.bogus.generator.Table

data class Relation(
    val name: String,
    val attributes: List<Attribute>
) {
    val fields: List<String>
        get() = attributes.map { it.field }

    fun generateTable(count: Int): Table {
        val map1: List<Column> = attributes.map { attribute ->
            attribute.generateColumn(count)
        }
        return Table(this, map1)
    }
}
