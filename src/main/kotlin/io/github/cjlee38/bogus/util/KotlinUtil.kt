package io.github.cjlee38.bogus.util

import java.math.BigInteger

fun String?.isEffectiveNull(): Boolean {
    return this.isNullOrEmpty() || this.equals("null", ignoreCase = true)
}

fun (() -> Any?).mixIn(outer: (() -> Any?) -> Any?): () -> Any? {
    return { outer(this) }
}

fun Any?.toInt(): Int {
    return this.toString().toInt(10)
}

fun Any?.toLong(): Long {
    return this.toString().toLong(10)
}

operator fun Int.compareTo(cardinality: BigInteger): Int {
    return BigInteger.valueOf(this.toLong()).compareTo(cardinality)
}
