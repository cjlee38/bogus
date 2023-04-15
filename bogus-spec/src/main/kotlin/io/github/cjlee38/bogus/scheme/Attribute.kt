package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.scheme.pattern.Pattern
import io.github.cjlee38.bogus.scheme.type.DataType
import io.github.cjlee38.bogus.scheme.type.Default
import io.github.cjlee38.bogus.scheme.type.Null
import io.github.cjlee38.database.Column


class Attribute(
    val field: String,
    val type: DataType<Any>,
    private val pattern: Pattern,
    val constraints: Constraints,
    val isPrimary: Boolean
) {
    lateinit var relation: Relation
    var reference: Reference? = null
        internal set(value) {
            field = value
            if (value != null) constraints.add(ForeignConstraint(value))
        }
    lateinit var column: Column

    val autoIncrement: Boolean
        get() = isPrimary && constraints.has(AutoIncrementConstraint::class)

    fun generateColumn(): Column {
        validateGeneratable()
        val generate = constraints.mixInConstraints { type.generate(pattern) }
        val values = (0 until relation.count).map { generate() }.map { format(it) }
        this.column = Column(field, isPrimary, values)
        return column
    }

    private fun format(any: Any): String {
        return when (any) {
            is Null -> "null"
            is Default -> toString()
            is Number -> toString()
            else -> "'${toString()}'"
        }
    }

    private fun validateGeneratable() {
        if (constraints.has(UniqueConstraint::class) && !constraints.has(NullableConstraint::class)) {
            if (relation.count > type.cardinality) {
                throw IllegalArgumentException("count(${relation.count}) cannot be bigger than cardinality(${type.cardinality})")
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
