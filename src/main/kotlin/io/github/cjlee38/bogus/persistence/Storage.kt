package io.github.cjlee38.bogus.persistence

import io.github.cjlee38.bogus.generator.Table

object Storage {
    private val store: MutableMap<String, Map<String, Any>> = mutableMapOf() // todo : consider concurrency

    fun save(table: Table) {
        store[table.relation.name] =
            table.relation.fields.withIndex().associate { (index, field) ->
                field to table.columns[index]
            }
    }
}
