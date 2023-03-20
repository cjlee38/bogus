package io.github.cjlee38.bogus.generator

import io.github.cjlee38.bogus.scheme.type.StringType
import org.apache.commons.lang3.RandomStringUtils

class StringTypeGenerator {

    fun generate(type: StringType): String {
        val random = RandomStringUtils.randomAlphanumeric(0, type.length)
        return random
    }
}
