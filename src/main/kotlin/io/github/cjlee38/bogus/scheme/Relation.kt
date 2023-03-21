package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.generator.Table

class Relation(
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Relation

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String {
        return "Relation(name='$name')"
    }
}
