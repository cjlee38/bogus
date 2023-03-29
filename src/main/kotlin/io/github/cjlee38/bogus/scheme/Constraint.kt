package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.scheme.type.Default
import io.github.cjlee38.bogus.scheme.type.Null
import io.github.cjlee38.bogus.util.mixIn
import kotlin.random.Random

sealed class Constraint(
    val priority: Int
) {
    abstract fun mixInConstraint(generate: () -> Any): () -> Any
}

class NullableConstraint(private val nullRatio: Double) : Constraint(1) {
    init {
        require(nullRatio in 0.0..1.0) { "null ratio $nullRatio must be in range 0.0 to 1.0 " }
    }

    override fun mixInConstraint(generate: () -> Any): () -> Any {
        return generate.mixIn {
            if (Random.nextDouble(0.0, 1.0) <= nullRatio) Null else it()
        }
    }
}

class UniqueConstraint(
    private val precedence: UniqueConstraint? = null // todo : multiple unique
) : Constraint(2) {
    private val set = mutableSetOf<Any?>()

    override fun mixInConstraint(generate: () -> Any): () -> Any {
        return generate.mixIn {
            while (true) {
                val gen = it()
                val add = set.add(gen)
                if (add) return@mixIn gen
            }
        }
    }
}

class DefaultConstraint : Constraint(3) {
    override fun mixInConstraint(generate: () -> Any): () -> Any {
        return generate.mixIn {
            val v = it()
            if (v is Null) return@mixIn Default else v
        }
    }
}

class AutoIncrementConstraint : Constraint(4) {
    override fun mixInConstraint(generate: () -> Any): () -> Any {
        return { Null }
    }
}

class ForeignConstraint(
    private val reference: Reference,
    private val relationshipRatio: Double
) : Constraint(5) {
    override fun mixInConstraint(generate: () -> Any): () -> Any {
        val refColumn = reference.referencedAttribute.column
        var index = 0

        return generate.mixIn {
            val v = refColumn.values[index]
            if (Random.nextDouble(0.0, 1.0) <= relationshipRatio) {
                index++
            }
            return@mixIn v
        }
    }
}

class CheckConstraint : Constraint(100) {
    override fun mixInConstraint(generate: () -> Any): () -> Any {
        TODO("Not yet implemented")
    }
}
