package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.config.RelationConfig
import io.github.cjlee38.bogus.generator.Column
import io.github.cjlee38.bogus.persistence.Storage
import io.github.cjlee38.bogus.scheme.pattern.NumberPattern
import io.github.cjlee38.bogus.scheme.pattern.Pattern
import io.github.cjlee38.bogus.scheme.type.DataType
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

    fun generateColumn(config: RelationConfig): Column {
        var generate = getSource(config)
        generate = nullHandler.handle(generate)
        generate = mixInRange(generate, config)

        val column = Column(this, generate() as List<Any?>)
        Storage.save(column)
        return column
    }

    private fun getSource(config: RelationConfig): () -> Any? {
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
            // assume that use_auto_increment is true if not defined
//            val stringPattern = StringPattern.SEQUENCE // todo : temp

            if (extra.autoIncrement && config.useAutoIncrement) return { null } // insert null if 'use_auto_increment' is true to get number from DBMS
//            else if (type is IntegerType) return { Sequence.get(this) }
//            else if (type is StringType) return { UUID.randomUUID() }
//            else if (pattern == Pattern.SEQUENCE) return { Sequence.get(this) }
            else return { type.generate(pattern) } // RANDOM
        }

        return { type.generate(NumberPattern.SEQUENTIAL) }
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
        return source.mixIn { if (Random.nextDouble(0.0, 1.0) <= nullRatio) null else it() }
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
