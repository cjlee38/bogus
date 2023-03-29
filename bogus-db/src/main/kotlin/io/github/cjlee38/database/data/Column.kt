package io.github.cjlee38.database.data

data class Column(val values: List<String>) {
    val size: Int
        get() = values.size
}
