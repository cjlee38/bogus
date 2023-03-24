package io.github.cjlee38.bogus.persistence

import io.github.cjlee38.bogus.scheme.Attribute
import io.github.cjlee38.bogus.scheme.type.DataType
import io.github.cjlee38.bogus.scheme.type.IntegerType
import java.util.concurrent.atomic.AtomicLong

object Sequence {
    private val seq: MutableMap<Attribute, AtomicLong> = mutableMapOf()
    private val seq2: MutableMap<DataType<*>, AtomicLong> = mutableMapOf()

    fun get(attribute: Attribute): Long {
        val atomic = seq.computeIfAbsent(attribute) { _ -> AtomicLong() }
        return atomic.addAndGet(1)
    }

    fun get(type: IntegerType): Long {
        val atomic = seq2.computeIfAbsent(type) { _ -> AtomicLong() }
        return atomic.addAndGet(1)
    }
}
