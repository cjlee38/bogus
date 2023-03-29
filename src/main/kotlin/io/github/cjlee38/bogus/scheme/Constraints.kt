package io.github.cjlee38.bogus.scheme

import kotlin.reflect.KClass

class Constraints(
    input: List<Constraint>
) {
    private val values: MutableList<Constraint>

    init {
        require(!(input.has(UniqueConstraint::class) && input.has(DefaultConstraint::class))) { "unique and default constraint are incompatible" }
        values = input.sortedBy { it.priority }.toMutableList()
    }

    fun mixInConstraints(source: () -> Any): () -> Any {
        return values.fold(source) { generate, constraint -> constraint.mixInConstraint(generate) }
    }

    fun has(klass: KClass<out Constraint>): Boolean {
        return values.has(klass)
    }

    fun add(constraint: Constraint) {
        values.add(constraint)
    }

    companion object {
        fun List<Constraint>.has(klass: KClass<out Constraint>): Boolean {
            return this.any { it::class == klass }
        }
    }
}
