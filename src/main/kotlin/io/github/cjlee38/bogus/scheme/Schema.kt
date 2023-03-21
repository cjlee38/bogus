package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.generator.Table

data class Schema(val relations: List<Relation>) {

    fun generate(): List<Table> {
        return relations.map { it.generateTable(5) }
    }
}
