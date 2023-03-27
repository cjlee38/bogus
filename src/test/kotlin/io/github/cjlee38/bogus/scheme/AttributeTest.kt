package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.config.RelationConfig
import io.github.cjlee38.bogus.scheme.pattern.NumberPattern
import io.github.cjlee38.bogus.scheme.pattern.Pattern
import io.github.cjlee38.bogus.scheme.pattern.StringPattern
import io.github.cjlee38.bogus.scheme.type.Default
import io.github.cjlee38.bogus.scheme.type.Null
import io.github.cjlee38.bogus.support.createAttribute
import io.github.cjlee38.bogus.support.createIntegerType
import io.github.cjlee38.bogus.support.createStringType
import io.github.cjlee38.bogus.util.toLong
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.inspectors.forAll
import io.kotest.inspectors.shouldForAll
import io.kotest.matchers.collections.shouldBeSortedWith
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldContainOnlyNulls
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.string.shouldHaveLength
import io.kotest.matchers.string.shouldHaveMaxLength
import io.kotest.matchers.types.shouldBeTypeOf

class AttributeTest : FreeSpec({

    "normal" - {
        val config = RelationConfig(count = 1000, useAutoIncrement = true)

        "random-integer" {
            val attribute = createAttribute(type = createIntegerType())
            val column = attribute.generateColumn(config)
            column.values.forAll { it.shouldBeTypeOf<Long>() }
        }

        "random-string" {
            val attribute = createAttribute(type = createStringType())
            val column = attribute.generateColumn(config)
            column.values.forAll {
                it.shouldBeTypeOf<String>()
                it shouldHaveMaxLength 255
            }
        }
    }

    "unique" - {
        val config = RelationConfig(count = 1000, useAutoIncrement = false)

        "not-null" {
            val attribute = createAttribute(
                type = createIntegerType(min = 0L, max = 10L),
                key = AttributeKey.UNIQUE,
                pattern = Pattern.RANDOM
            )
            shouldThrow<IllegalArgumentException> {
                attribute.generateColumn(config)
            }
        }

        "nullable" {
            val attribute = createAttribute(
                type = createIntegerType(min = 0L, max = 10L),
                key = AttributeKey.UNIQUE,
                pattern = Pattern.RANDOM,
                nullHandler = AttributeNullHandler(true, 0.5, Null)
            )
            val column = attribute.generateColumn(config)
            column.values shouldContainAll (0L until 10L).toList()
            column.values.filterIsInstance<Null>() shouldHaveSize config.count - (0 until 10L).toList().size
        }

        "default value" {
            shouldThrow<java.lang.IllegalArgumentException> {
                createAttribute(
                    key = AttributeKey.UNIQUE,
                    nullHandler = AttributeNullHandler(true, 0.5, 987)
                )
            }
        }
    }

    "primary" - {
        "dbms-generated" - {
            val config = RelationConfig(count = 100, useAutoIncrement = true)

            "auto-increment" {
                val attribute = createAttribute(key = AttributeKey.PRIMARY)
                val column = attribute.generateColumn(config)
                column.values.shouldForAll { it is Null }
            }
            // todo : @GeneratedValue(sequence, table)
        }

        "user-generated" - {
            val config = RelationConfig(count = 100, useAutoIncrement = false)
            val extra = Extra(autoIncrement = false)

            "number" - {
                "random" {
                    val attribute = createAttribute(key = AttributeKey.PRIMARY, extra = extra, pattern = Pattern.RANDOM)
                    val column = attribute.generateColumn(config)
                    println(column.values)
                }

                "sequential" {
                    val attribute =
                        createAttribute(key = AttributeKey.PRIMARY, extra = extra, pattern = NumberPattern.SEQUENTIAL)
                    val column = attribute.generateColumn(config)
                    column.values shouldBeSortedWith { a, b -> a.toLong().compareTo(b.toLong()) }
                }
            }

            "string" - {
                "random" {
//                    val attribute = createAttribute(type = createStringType(), key = AttributeKey.PRIMARY, extra = extra)
//                    attribute.generateColumn(RelationConfig(count = 100, useAutoIncrement = false, listOf(AttributeConfig())))
                }

                "uuid" {
                    val attribute =
                        createAttribute(
                            type = createStringType(),
                            key = AttributeKey.PRIMARY,
                            extra = extra,
                            pattern = StringPattern.UUID
                        )
                    val column = attribute.generateColumn(config)
                    column.values.forAll {
                        it.shouldBeTypeOf<String>()
                        it.shouldHaveLength(36)
                    }
                }

                "regex".config(enabled = false) {
                    TODO()
                }
            }
        }
    }
})
