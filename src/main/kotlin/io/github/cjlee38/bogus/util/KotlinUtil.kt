package io.github.cjlee38.bogus.util

fun String?.isEffectiveNull(): Boolean {
    return this.isNullOrEmpty() || this.equals("null", ignoreCase = true)
}

fun (() -> Any?).mixIn(outer: (() -> Any?) -> Any?): () -> Any? {
    return { outer(this) }
}

fun Any?.toInt(): Int {
    return this.toString().toInt(10)
}
