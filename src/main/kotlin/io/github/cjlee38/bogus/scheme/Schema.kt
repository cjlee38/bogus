package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.config.RelationConfig
import io.github.cjlee38.bogus.generator.Table

data class Schema(
    val relations: List<Relation>,
) {
    fun iterate(): Iterator<Table> {
        return iterator {
            val size = relations.size
            for (i in 0 until size) {
                yield(relations[i].generateTable(RelationConfig()))
            }
        }
    }
}
