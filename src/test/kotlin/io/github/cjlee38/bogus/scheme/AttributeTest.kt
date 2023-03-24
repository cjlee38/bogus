package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.config.RelationConfig
import io.github.cjlee38.bogus.support.createAttribute
import io.github.cjlee38.bogus.support.createIntegerType
import io.github.cjlee38.bogus.support.createStringType
import io.github.cjlee38.bogus.util.toInt
import io.kotest.core.spec.style.FreeSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.collections.shouldBeSortedWith
import io.kotest.matchers.collections.shouldContainOnlyNulls
import io.kotest.matchers.string.shouldHaveLength
import io.kotest.matchers.string.shouldHaveMaxLength
import io.kotest.matchers.types.shouldBeTypeOf
import java.util.UUID

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

    "primary" - {
        "dbms-generated" - {
            val config = RelationConfig(count = 100, useAutoIncrement = true)

            "auto-increment" - {
                val attribute = createAttribute(key = AttributeKey.PRIMARY)
                val column = attribute.generateColumn(config)
                column.values.shouldContainOnlyNulls()
            }
            // todo : @GeneratedValue(sequence, table)
        }

        "user-generated" - {
            val config = RelationConfig(count = 100, useAutoIncrement = false)
            val extra = Extra(autoIncrement = false)

            "sequence" {
                val attribute = createAttribute(key = AttributeKey.PRIMARY, extra = extra)
                val column = attribute.generateColumn(config)
                column.values shouldBeSortedWith { a, b -> a.toInt().compareTo(b.toInt()) }
            }

            "uuid" {
                val attribute = createAttribute(type = createStringType(), key = AttributeKey.PRIMARY, extra = extra)
                val column = attribute.generateColumn(config)
                column.values.forAll { it.shouldBeTypeOf<UUID>() }
            }
        }
    }


//    context("uuid") {
//
//    }
})
