package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.config.RelationConfig
import io.github.cjlee38.bogus.generator.Column
import io.github.cjlee38.bogus.persistence.Storage
import io.github.cjlee38.bogus.scheme.pattern.Pattern
import io.github.cjlee38.bogus.scheme.type.DataType
import io.github.cjlee38.bogus.scheme.type.Null
import io.github.cjlee38.bogus.util.compareTo
import io.github.cjlee38.bogus.util.mixIn
import kotlin.random.Random


class Attribute(
    val field: String,
    val type: DataType<*>,
    val key: AttributeKey,
    val extra: Extra,
    private val pattern: Pattern,
    private val nullHandler: AttributeNullHandler,
) {
    lateinit var relation: Relation
    var reference: Reference? = null
        internal set

    init {
        require(!(key.isUnique && nullHandler.default !is Null)) { "mixing unique and default value is prohibited in field $field" }
    }

    fun generateColumn(config: RelationConfig): Column {
        var generate = getSource(config)
        generate = nullHandler.handle(generate)
        generate = mixInRange(generate, config)

        val column = Column(this, generate() as List<Any?>)
        Storage.save(column)
        return column
    }

    private fun getSource(config: RelationConfig): () -> Any? {
        if (key == AttributeKey.UNIQUE && !nullHandler.isNullable) {
            if (config.count > type.cardinality) {
                throw IllegalArgumentException("count(${config.count}) cannot be bigger than cardinality (${type.cardinality})")
            }
        }

        reference?.let {
            val refColumn = Storage.find(it.referencedRelation, it.referencedAttribute)
                ?: throw IllegalArgumentException("unexpected exception : ${it.referencedRelation} ${it.referencedAttribute}")
            if (this.key == AttributeKey.PRIMARY || it.referencedAttribute.key == AttributeKey.PRIMARY) {
                val iterator = refColumn.values.iterator()
                return { iterator.next() }
            }
            return { refColumn.values.random() }
        }

        if (key == AttributeKey.PRIMARY) {
            if (extra.autoIncrement && config.useAutoIncrement) return { Null } // insert null if 'use_auto_increment' is true to get number from DBMS
            else return { type.generate(pattern) } // RANDOM
        }

        return { type.generate(pattern) }
    }

    private fun mixInRange(generate: () -> Any?, config: RelationConfig): () -> Any? {
        return {
            val set = mutableSetOf<Any?>()
            while (set.size < config.count) {
                set.add(generate())
            }
            set.toList()
        }
    }

    private fun mixInNullable(source: () -> Any?): () -> Any? {
        val nullRatio = 0.1 // todo : get ratio from user confiugration
        return source.mixIn { if (Random.nextDouble(0.0, 1.0) <= nullRatio) Null else it() }
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
