package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.scheme.pattern.Pattern
import io.github.cjlee38.bogus.scheme.type.Null
import io.github.cjlee38.bogus.support.createAttribute
import io.github.cjlee38.bogus.support.createConstraints
import io.github.cjlee38.bogus.support.createIntegerType
import io.github.cjlee38.bogus.support.createStringType
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.inspectors.forAll
import io.kotest.inspectors.shouldForAll
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.string.shouldHaveMaxLength
import io.kotest.matchers.types.shouldBeTypeOf

class AttributeTest : FreeSpec({

    "normal" - {

        "random-integer" {
            val attribute = createAttribute(type = createIntegerType())
            val column = attribute.generateColumn()
            column.values.forAll { it.shouldBeTypeOf<Long>() }
        }

        "random-string" {
            val attribute = createAttribute(type = createStringType())
            val column = attribute.generateColumn()
            column.values.forAll {
                it.shouldBeTypeOf<String>()
                it shouldHaveMaxLength 255
            }
        }
    }

    "unique" - {
        "not-null" {
            val attribute = createAttribute(
                type = createIntegerType(min = 0L, max = 10L),
                pattern = Pattern.RANDOM,
                constraints = createConstraints(UniqueConstraint())
            )
            shouldThrow<IllegalArgumentException> {
                attribute.generateColumn()
            }
        }

        "nullable" {
            val attribute = createAttribute(
                type = createIntegerType(min = 0L, max = 10L),
                pattern = Pattern.RANDOM,
                constraints = createConstraints(UniqueConstraint(), NullableConstraint(0.5))
            )
            val column = attribute.generateColumn()
            column.values shouldContainAll (0L until 10L).toList()
            column.values.filterIsInstance<Null>() shouldHaveSize attribute.relation.count - (0 until 10L).toList().size
        }

        "default value" {
//            shouldThrow<IllegalArgumentException> {
//                createAttribute(
//                    key = AttributeKey.UNIQUE,
//                    nullHandler = AttributeNullHandler(true, 0.5, 987)
//                )
//            }
        }
    }

    "primary" - {
        "dbms-generated" - {
            "auto-increment" {
                val attribute = createAttribute(constraints = createConstraints(AutoIncrementConstraint()))
                val column = attribute.generateColumn()
                column.values.shouldForAll { it is Null }
            }
            // todo : @GeneratedValue(sequence, table)
        }

//        "user-generated" - {
//            val config = RelationConfig(count = 100, useAutoIncrement = false)
//            val extra = Extra(autoIncrement = false)
//
//            "number" - {
//                "random" {
//                    val attribute = createAttribute(pattern = Pattern.RANDOM)
//                    val column = attribute.generateColumn()
//                    println(column.values)
//                }
//
//                "sequential" {
//                    val attribute =
//                        createAttribute(key = AttributeKey.PRIMARY, extra = extra, pattern = NumberPattern.SEQUENTIAL)
//                    val column = attribute.generateColumn()
//                    column.values shouldBeSortedWith { a, b -> a.toLong().compareTo(b.toLong()) }
//                }
//            }
//
//            "string" - {
//                "random" {
////                    val attribute = createAttribute(type = createStringType(), key = AttributeKey.PRIMARY, extra = extra)
////                    attribute.generateColumn(RelationConfig(count = 100, useAutoIncrement = false, listOf(AttributeConfig())))
//                }
//
//                "uuid" {
//                    val attribute =
//                        createAttribute(
//                            type = createStringType(),
//                            key = AttributeKey.PRIMARY,
//                            extra = extra,
//                            pattern = StringPattern.UUID
//                        )
//                    val column = attribute.generateCoelumn(config)
//                    column.values.forAll {
//                        it.shouldBeTypeOf<String>()
//                        it.shouldHaveLength(36)
//                    }
//                }
//
//                "regex".config(enabled = false) {
//                    TODO()
//                }
//            }
//        }
    }
})
