package io.github.cjlee38.bogus.support

import io.github.cjlee38.bogus.scheme.Attribute
import io.github.cjlee38.bogus.scheme.Relation

fun createRelation(
    vararg attributes: Attribute,
    count: Int = 100
) : Relation {
    return Relation("fixture-relation", attributes = attributes.asList(), count)
}
