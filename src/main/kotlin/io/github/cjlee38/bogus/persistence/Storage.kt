package io.github.cjlee38.bogus.persistence

import io.github.cjlee38.bogus.generator.Column
import io.github.cjlee38.bogus.generator.Table
import io.github.cjlee38.bogus.scheme.Attribute
import io.github.cjlee38.bogus.scheme.Relation

object Storage {
    private val store: MutableMap<Relation, MutableMap<Attribute, Column>> = mutableMapOf() // todo : consider concurrency

    fun save(table: Table) {
        store[table.relation] = table.relation.attributes
            .withIndex()
            .associate { (index, attribute) -> attribute to table.columns[index] }
            .apply { this.values.forEach { it.cached = true }  }
            .toMutableMap()
    }

    fun save(column: Column) {
        val attribute = column.attribute
        if (store[attribute.relation] == null) {
            store[attribute.relation] = mutableMapOf()
        }
        store[attribute.relation]!![attribute] = column
        column.cached = true
    }

    fun find(relation: Relation, attribute: Attribute): Column? {
        return store[relation]?.get(attribute)
    }

    fun clear() {
        store.clear()
    }
}
