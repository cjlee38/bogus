package io.github.cjlee38.bogus.persistence

import io.github.cjlee38.bogus.support.createIntegerType
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class SequenceTest : StringSpec({
    "get" {
        val seq = Sequence.get(createIntegerType())

        seq shouldBe 1L
    }

    "get from same address" {
        val type = createIntegerType()

        val ignored = Sequence.get(type)
        val seq = Sequence.get(type)

        seq shouldBe 2L
    }

    "get from diffrent address" {
        val ignored = Sequence.get(createIntegerType())
        val seq = Sequence.get(createIntegerType())

        seq shouldBe 1L
    }
})
