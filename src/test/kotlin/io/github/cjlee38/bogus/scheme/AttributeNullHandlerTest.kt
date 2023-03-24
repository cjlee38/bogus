package io.github.cjlee38.bogus.scheme

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class AttributeNullHandlerTest : StringSpec({

    "not-null" {
        val source = { 1 }
        val nullHandler = AttributeNullHandler(isNullable = false, nullRatio = 0.0, default = null)

        val handle = nullHandler.handle(source)

        handle() shouldBe 1
    }

    "null" {
        val source = { 1 }
        val nullHandler = AttributeNullHandler(isNullable = true, nullRatio = 1.0, default = null)

        val handle = nullHandler.handle(source)

        handle().shouldBeNull()
    }

    "default" {
        val source = { 1 }
        val nullHandler = AttributeNullHandler(isNullable = false, nullRatio = 1.0, default = 2)

        val handle = nullHandler.handle(source)

        handle() shouldBe 2
    }

    "null if nullable although default" {
        val source = { 1 }
        val nullHandler = AttributeNullHandler(isNullable = true, nullRatio = 1.0, default = 2)

        val handle = nullHandler.handle(source)

        handle().shouldBeNull()
    }
})
