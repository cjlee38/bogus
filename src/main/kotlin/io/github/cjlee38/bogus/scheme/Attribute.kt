package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.generator.Column
import io.github.cjlee38.bogus.persistence.Sequence
import io.github.cjlee38.bogus.persistence.Storage
import io.github.cjlee38.bogus.scheme.type.DType
import io.github.cjlee38.bogus.util.mixIn
//import io.github.cjlee38.bogus.util.mixIn
import kotlin.random.Random


class Attribute(
    val field: String,
    val type: DType,
    val isNullable: Boolean,
    val key: String?,
    val default: String?,
    val extra: String?
) {
    lateinit var relation: Relation
    var reference: Reference? = null
        internal set
    val isPrimary: Boolean
        get() = key == "pri"

    fun generateColumn(count: Int): Column {
//        val cached = Storage.find(relation, this)
//        if (cached != null) {
//            return cached
//        }
        var generate = getSource()
        if (isNullable) generate = mixInNullable(generate)

        val column = Column(this, (0 until count).map { generate() })
        Storage.save(this, column)
        return column
    }

    private fun mixInNullable(source: () -> Any?): () -> Any? {
        val nullRatio = 0.1 // todo : get ratio from user confiugration
        return source.mixIn { if (Random.nextDouble(0.0, 1.0) <= nullRatio) it() else null }
    }

    private fun getSource(): () -> Any? {
        val ref = reference
        if (ref != null) {
            val refColumn = Storage.find(ref.referencedRelation, ref.referencedAttribute)
                ?: ref.referencedAttribute.generateColumn(5) // this is temporary
            return { refColumn.values.random() }
        }
        if (isPrimary) {
            val useAutoIncrement = true // todo : get from user configuration
            if (useAutoIncrement) {

            } else {
                return { Sequence.get(this) }
            }
        }
        return { type.generateRandom() }
    }
}
