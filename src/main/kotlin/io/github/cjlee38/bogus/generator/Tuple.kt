package io.github.cjlee38.bogus.generator

data class Tuple(val values: List<Any?>) {
    constructor(vararg values: Any?) : this(values.asList())
}
