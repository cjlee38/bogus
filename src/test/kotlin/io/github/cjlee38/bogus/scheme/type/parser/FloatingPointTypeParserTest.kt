package io.github.cjlee38.bogus.scheme.type.parser

import io.github.cjlee38.bogus.scheme.type.FloatingPointType
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class FloatingPointTypeParserTest : StringSpec({
    val parser = FloatingPointTypeParser()

    "float" {
        val type = parser.parse("float")
        type shouldBe FloatingPointType(Float.MIN_VALUE.toDouble(), Float.MAX_VALUE.toDouble())
    }

    "double" {
        val type = parser.parse("double")
        type shouldBe FloatingPointType(Double.MIN_VALUE, Double.MAX_VALUE)
    }
})
