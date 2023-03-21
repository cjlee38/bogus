package io.github.cjlee38.bogus.util

fun String?.isEffectiveNull(): Boolean {
    return this.isNullOrEmpty() || this.equals("null", ignoreCase = true)
}
