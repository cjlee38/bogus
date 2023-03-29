package io.github.cjlee38.bogus.generator

import io.github.cjlee38.bogus.scheme.Attribute

data class Column(val attribute: Attribute, val values: List<Any>) {
    val size: Int
        get() = values.size
}
