package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.database.Table

class Relation(
    val name: String,
    val count: Int,
    val attributes: List<Attribute>
) {
    init {
        attributes.forEach { it.relation = this }
    }

    val fields: List<String>
        get() = attributes.map { it.field }
    val primaryAttribute: Attribute?
        get() = attributes.find { it.isPrimary }

    fun generateTable(): Table {
        return Table(
            name = name,
            rowCount = count,
            columns = attributes.map { it.generateColumn() },
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
