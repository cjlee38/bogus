package io.github.cjlee38.bogus.scheme.type

object Null {
    override fun equals(other: Any?): Boolean {
        return false
    }

    override fun hashCode(): Int {
        return System.nanoTime().toInt()
    }

    override fun toString(): String {
        return "null"
    }
}

