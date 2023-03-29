package io.github.cjlee38.bogus.util

import io.github.cjlee38.bogus.scheme.type.IntegerType
import java.util.concurrent.atomic.AtomicLong

object Sequence {
    private val sequenceByMemoryAddress: MutableMap<Int, AtomicLong> = mutableMapOf()

    fun get(type: IntegerType): Long {
        val memoryAddress = System.identityHashCode(type)
        val atomic = sequenceByMemoryAddress.computeIfAbsent(memoryAddress) { _ -> AtomicLong() }
        return atomic.addAndGet(1)
    }
}
