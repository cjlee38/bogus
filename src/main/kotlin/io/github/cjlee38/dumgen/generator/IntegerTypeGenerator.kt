package io.github.cjlee38.dumgen.generator

import io.github.cjlee38.dumgen.scheme.type.IntegerType
import java.util.concurrent.ThreadLocalRandom

class IntegerTypeGenerator {

    fun generate(type: IntegerType): Int {
        val random = ThreadLocalRandom.current()
        val next = random.nextInt(type.min.toInt(), type.max.toInt())
        return next
    }
}
