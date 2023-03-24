package io.github.cjlee38.bogus.scheme.pattern

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PatternTest : StringSpec({
    "null -> random" {
        val pattern = Pattern.of(null)

        pattern shouldBe Pattern.RANDOM
    }

    "uuid" {
        val pattern = Pattern.of("uuid")

        pattern shouldBe StringPattern.UUID
    }

    "regex" {
        val pattern = Pattern.of("regex")

        pattern shouldBe StringPattern.REGEX
    }

    "sequential" {
        val pattern = Pattern.of("sequential")

        pattern shouldBe NumberPattern.SEQUENTIAL
    }
})
