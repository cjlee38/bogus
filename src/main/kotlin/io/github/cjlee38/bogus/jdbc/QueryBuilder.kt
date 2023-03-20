package io.github.cjlee38.bogus.jdbc

import io.github.cjlee38.bogus.generator.Table
import io.github.cjlee38.bogus.scheme.Relation

interface QueryBuilder {
    fun build(relation: Relation, table: Table)
}
