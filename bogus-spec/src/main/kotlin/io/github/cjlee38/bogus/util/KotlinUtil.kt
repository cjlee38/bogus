package io.github.cjlee38.bogus.util

import java.math.BigInteger
import kotlin.math.pow

fun (() -> Any).mixIn(outer: (() -> Any) -> Any): () -> Any {
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

fun Long.pow(n: Int): Long {
    return this.toDouble().pow(n).toLong()
}
