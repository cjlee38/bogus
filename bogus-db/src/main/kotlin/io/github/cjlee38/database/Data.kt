package io.github.cjlee38.database

data class Table(
    val name: String,
    val rowCount: Int,
    val columns: List<Column>,
) {
    init {
        require(columns.all { it.size == rowCount }) { "columns has different size : ${columns.map { it.size }}" }
    }

    val tuples: List<Tuple>
        get() = transformToTuple()
    val fields: List<String>
        get() = columns.map { it.field }
    val primary: Column?
        get() = columns.find { it.isPrimary }

    private fun transformToTuple(): List<Tuple> {
        return (0 until rowCount).map { index ->
            columns.map { it.values[index] }.let(::Tuple)
        }
    }
}

data class Column(val field: String, val isPrimary: Boolean, val values: List<String>) {
    val size: Int
        get() = values.size
}

data class Tuple(val values: List<String>) {
    operator fun get(index: Int): Any {
        return values[index]
    }
}

