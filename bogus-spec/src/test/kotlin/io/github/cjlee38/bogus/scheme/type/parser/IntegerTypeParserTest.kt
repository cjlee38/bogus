package io.github.cjlee38.bogus.scheme.type.parser

import io.github.cjlee38.bogus.scheme.type.IntegerType
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class IntegerTypeParserTest {
    private val parser: IntegerTypeParser = IntegerTypeParser()

    @Test
    fun tinyint() {
        val type = parser.parse("tinyint")

        type shouldBe IntegerType(false, -128, 127)
    }

    @Test
    fun smallint() {
        val type = parser.parse("smallint")

        type shouldBe IntegerType(false, -32768, 32767)
    }

    @Test
    fun mediumint() {
        val type = parser.parse("mediumint")

        type shouldBe IntegerType(false, -8388608, 8388607)
    }

    @Test
    fun int() {
        val type = parser.parse("int")

        type shouldBe IntegerType(false, Int.MIN_VALUE.toLong(), Int.MAX_VALUE.toLong())
    }

    @Test
    fun bigint() {
        val type = parser.parse("bigint")

        type shouldBe IntegerType(false, Long.MIN_VALUE, Long.MAX_VALUE)
    }

    @Test
    fun bigint_unsigned() {
        val type = parser.parse("bigint unsigned")

        type shouldBe IntegerType(true, Long.MIN_VALUE, Long.MAX_VALUE)
    }
}
