package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.config.SchemaConfiguration
import io.github.cjlee38.database.Table

data class Schema(
    val relations: List<Relation>,
    val schemaConfiguration: SchemaConfiguration
) {
    fun iterate(): Iterator<Table> {
        return iterator {
            val size = relations.size
            for (i in 0 until size) {
                yield(relations[i].generateTable())
            }
        }
    }
}
