package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.config.RelationConfig
import io.github.cjlee38.bogus.generator.Column
import io.github.cjlee38.bogus.persistence.Sequence
import io.github.cjlee38.bogus.persistence.Storage
import io.github.cjlee38.bogus.scheme.pattern.Pattern
import io.github.cjlee38.bogus.scheme.type.DType
import io.github.cjlee38.bogus.util.mixIn
import kotlin.random.Random


class Attribute(
    val field: String,
    val type: DType,
    val isNullable: Boolean,
    val key: String?,
    val default: String?,
    val extra: Extra
) {
    lateinit var relation: Relation
    var reference: Reference? = null
        internal set
    val isPrimary: Boolean
        get() = key == "pri"

    fun generateColumn(config: RelationConfig): Column {
        var generate = getSource(config)
        if (isNullable) generate = mixInNullable(generate)
        generate = mixInRange(generate, config)

        val column = Column(this, generate() as List<Any?>)
        Storage.save(column)
        return column
    }

    private fun mixInRange(generate: () -> Any?, config: RelationConfig): () -> Any? {
        val isUnique = true // todo : temp
        if (isUnique) {
            return {
                val list = mutableListOf<Any?>()
                while (list.size < config.count) {
                    list.add(generate())
                }
                list
            }
        } else {
            return generate.mixIn { (0 until config.count).map { it() } }
        }
    }

    private fun mixInNullable(source: () -> Any?): () -> Any? {
        val nullRatio = 0.1 // todo : get ratio from user confiugration
        return source.mixIn { if (Random.nextDouble(0.0, 1.0) <= nullRatio) it() else null }
    }

    private fun getSource(config: RelationConfig): () -> Any? {
        val ref = reference
        if (ref != null) {
            val refColumn = Storage.find(ref.referencedRelation, ref.referencedAttribute)
                ?: throw IllegalArgumentException("unexpected exception : ${ref.referencedRelation} ${ref.referencedAttribute}")
            return { refColumn.values.random() }
        }

        if (isPrimary) {
            // assume that use_auto_increment is true if not defined
            val pattern = Pattern.SEQUENCE // todo : temp

            if (extra.autoIncrement && config.useAutoIncrement) return { null } // insert null if you use_auto_increment true to get number from DBMS
            else if (pattern == Pattern.SEQUENCE) return { Sequence.get(this) }
            else return { type.generateRandom() } // RANDOM
        }

        return { type.generateRandom() }
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
