package io.github.cjlee38.bogus.generator

import io.github.cjlee38.bogus.scheme.Relation


data class Table(
    val relation: Relation,
    val columns: List<Column>,
) {
    // todo : row count ?
    init {
        val size = columns[0].size
        require(columns.all { it.size == size }) { "columns has different size : ${columns.map { it.size }}" }
    }

    val tuples: List<Tuple>
        get() = transformToTuple()

    private fun transformToTuple(): List<Tuple> {
        val size = columns[0].size
        return (0 until size).map { index ->
            columns.map { it.values[index] }.let(::Tuple)
        }
    }
}
