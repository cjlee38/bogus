package io.github.cjlee38.bogus.scheme.type

interface DataType<T> {
    fun generateRandom(): T
}
