package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.config.RelationConfig
import io.github.cjlee38.bogus.generator.Column
import io.github.cjlee38.bogus.persistence.Storage
import io.github.cjlee38.bogus.scheme.pattern.Pattern
import io.github.cjlee38.bogus.scheme.type.DataType


class Attribute(
    val field: String,
    val type: DataType<Any>,
    private val pattern: Pattern,
    private val constraints: Constraints,
    val isPrimary: Boolean
) {
    lateinit var relation: Relation
    var reference: Reference? = null
        internal set
    lateinit var column: Column

    fun generateColumn(config: RelationConfig): Column {
        validateGeneratable(config)
        val generate = constraints.mixInConstraints { type.generate(pattern) }
        val map = (0 until config.count).map { generate() }
        this.column = Column(this, map)
        return column
    }

    private fun validateGeneratable(config: RelationConfig) {
        if (constraints.has(UniqueConstraint::class) && !constraints.has(NullableConstraint::class)) {
            if (config.count > type.cardinality) {
                throw IllegalArgumentException("count(${config.count}) cannot be bigger than cardinality(${type.cardinality})")
            }
        }
    }

    override fun toString(): String {
        return "Attribute(field='$field', type=$type)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Attribute

        if (field != other.field) return false
        if (relation != other.relation) return false

        return true
    }

    override fun hashCode(): Int {
        var result = field.hashCode()
        result = 31 * result + relation.hashCode()
        return result
    }
}
