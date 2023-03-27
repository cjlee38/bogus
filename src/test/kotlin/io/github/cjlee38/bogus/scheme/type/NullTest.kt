package io.github.cjlee38.bogus.scheme.type

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class NullTest : StringSpec({

    "equals" {
        (Null == Null).shouldBeFalse()
    }

    "list - distinct" {
        val list = listOf(1, 1, 1, Null, 2, 2, Null, 3, 3, 3)
        val distinct = list.distinct()
        distinct shouldHaveSize 5
    }

    "set" {
        val set = mutableSetOf<Any>()
        for (i in 0 until 10) set.add(Null)

        set shouldHaveSize 10
    }

    "identity-hashcode" {
        val hashCodeA = System.identityHashCode(Null)
        val hashCodeB = System.identityHashCode(Null)
        hashCodeA shouldBe hashCodeB
    }
})

