package io.github.cjlee38.bogus.generator

data class Tuple(val values: List<Any?>) {
    operator fun get(index: Int): Any? {
        return values[index]
    }

    fun format(): List<String?> {
        return values.map {
            when(it) {
                null -> null
                is Number -> it.toString()
                else -> "'$it'"
            }
        }
    }
}
