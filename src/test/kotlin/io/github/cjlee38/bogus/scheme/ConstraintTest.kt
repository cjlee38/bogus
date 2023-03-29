package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.scheme.pattern.NumberPattern
import io.github.cjlee38.bogus.scheme.type.Default
import io.github.cjlee38.bogus.scheme.type.Null
import io.github.cjlee38.bogus.support.createAttribute
import io.github.cjlee38.bogus.support.createReference
import io.github.cjlee38.bogus.support.createRelation
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe

class ConstraintTest : FreeSpec({
    val value = 1

    "nullable constraints" - {
        "invalid range" {
            shouldThrow<IllegalArgumentException> { NullableConstraint(-0.1) }
            shouldThrow<IllegalArgumentException> { NullableConstraint(1.1) }
        }

        "nullable" {
            val constraint = NullableConstraint(1.0)
            val expected = constraint.mixInConstraint { value }()
            expected shouldBe Null
        }

        "not-null" {
            val constraint = NullableConstraint(0.0)
            val expected = constraint.mixInConstraint { value }()
            expected shouldBe value
        }
    }

    "unique" - {
        "get unique" {
            val constraint = UniqueConstraint()
            val range = 0 until 10
            val iterator = range.iterator()

            val expected = range.map { constraint.mixInConstraint { iterator.next() }() }
            expected shouldContainExactly range.toList()
        }

        "remove duplicates" {
            val constraint = UniqueConstraint()
            val list = listOf(value, 2, 2, 2)
            val iterator = list.iterator()

            shouldThrow<NoSuchElementException> { (list.indices).map { constraint.mixInConstraint { iterator.next() }() } }
        }
    }

    "default" - {
        "get default if null" {
            val constraint = DefaultConstraint()
            val expected = constraint.mixInConstraint { Null }()
            expected shouldBe Default
        }

        "get value if not null" {
            val constraint = DefaultConstraint()
            val expected = constraint.mixInConstraint { value }()
            expected shouldBe value
        }
    }

    "primary" - {
        "get null if auto increment" {
            val constraint = AutoIncrementConstraint()
            val expected = constraint.mixInConstraint { value }()
            expected shouldBe Null
        }
    }

    "foreign" - {
        "one to one" {
            val reference = createReference(referencedAttribute = createAttribute(pattern = NumberPattern.SEQUENTIAL))
            val column = reference.referencedAttribute.generateColumn()

            val constraint = ForeignConstraint(reference = reference)
            val generate = constraint.mixInConstraint { value }
            val expected = (column.values.indices).map { generate() }
            expected shouldContainExactly column.values
        }

        "one to many" {
            val referencedAttribute = createAttribute(pattern = NumberPattern.SEQUENTIAL)
            referencedAttribute.relation = createRelation(count = 10)
            val attribute = createAttribute()
            attribute.relation = createRelation(count = 100)

            val reference = createReference(attribute, referencedAttribute)
            val column = referencedAttribute.generateColumn()

            val constraint = ForeignConstraint(reference = reference)
            val generate = constraint.mixInConstraint { value }
            val expected = (0 until 100).map { generate() }
            column.values.shouldContainAll(expected)
        }
    }
})
