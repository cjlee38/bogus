package io.github.cjlee38.database.data

import io.github.cjlee38.bogus.scheme.Relation


data class Table(
//    val relation: Relation,
    val rowCount: Int,
    val columns: List<Column>,
) {
    init {
        require(columns.all { it.size == rowCount }) { "columns has different size : ${columns.map { it.size }}" }
    }

    val tuples: List<Tuple>
        get() = transformToTuple()

    private fun transformToTuple(): List<Tuple> {
        return (0 until rowCount).map { index ->
            columns.map { it.values[index] }.let(::Tuple)
        }
    }
}
