package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.generator.Table
import io.github.cjlee38.bogus.generator.Tuple

data class Relation(
    val name: String,
    val attributes: List<Attribute>
) {
    val fields: List<String>
        get() = attributes.map { it.field }

    fun generateRandom(count: Int): Table {
        return (0 until count).map {
            attributes
                .map { it.generateRandom() }
                .let(::Tuple)
        }.let(::Table)
    }
}
