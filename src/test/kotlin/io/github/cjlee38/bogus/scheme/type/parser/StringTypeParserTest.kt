package io.github.cjlee38.bogus.scheme.type.parser

import io.github.cjlee38.bogus.scheme.type.StringType
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class StringTypeParserTest : StringSpec({
    val parser = StringTypeParser()

    "char" {
        val type = parser.parse("char(20)")
        type shouldBe StringType(false, 20)
    }

    "varchar" {
        val type = parser.parse("varchar(255)")
        type shouldBe StringType(true, 255)
    }
})
