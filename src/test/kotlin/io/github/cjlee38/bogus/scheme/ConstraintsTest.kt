package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.scheme.type.Null
import io.github.cjlee38.bogus.support.createConstraints
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class ConstraintsTest : FreeSpec({
    "init" - {
//        "nullable + primary" {
//            shouldThrow<IllegalArgumentException> {
//                createConstraints(NullableConstraint(0.5), AutoIncrementConstraint())
//            }
//        }

        "unique + default" {
            shouldThrow<IllegalArgumentException> {
                createConstraints(UniqueConstraint(), DefaultConstraint())
            }
        }
    }

    "mixInConstraints" - {
        "test" {
            val constraints = createConstraints(UniqueConstraint(), NullableConstraint(1.0))
            val expected = constraints.mixInConstraints { 1 }()
            expected shouldBe Null
        }
    }
})
