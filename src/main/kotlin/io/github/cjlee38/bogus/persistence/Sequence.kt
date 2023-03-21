package io.github.cjlee38.bogus.persistence

import io.github.cjlee38.bogus.scheme.Attribute
import java.util.concurrent.atomic.AtomicLong

object Sequence {
    private val seq: MutableMap<Attribute, AtomicLong> = mutableMapOf()

    fun get(attribute: Attribute): Long {
        val atomic = seq.computeIfAbsent(attribute) { _ -> AtomicLong() }
        return atomic.addAndGet(1)
    }
}
