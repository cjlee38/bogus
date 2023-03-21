package io.github.cjlee38.bogus.persistence

import io.github.cjlee38.bogus.generator.Column
import io.github.cjlee38.bogus.generator.Table
import io.github.cjlee38.bogus.scheme.Attribute
import io.github.cjlee38.bogus.scheme.Relation

object Storage {
    private val store: MutableMap<Relation, Map<Attribute, Column>> = mutableMapOf() // todo : consider concurrency

    fun save(table: Table) {
        store[table.relation] = table.relation.attributes
            .withIndex()
            .associate { (index, attribute) -> attribute to table.columns[index] }
    }

    fun save(attribute: Attribute, column: Column) {
        store[attribute.relation] = mapOf(attribute to column)
    }

    fun find(relation: Relation, attribute: Attribute): Column? {
        return store[relation]?.get(attribute)
    }

    fun clear() {
        store.clear()
    }
}
