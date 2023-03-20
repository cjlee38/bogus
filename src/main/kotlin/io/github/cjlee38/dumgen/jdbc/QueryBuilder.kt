package io.github.cjlee38.dumgen.jdbc

import io.github.cjlee38.dumgen.generator.Table
import io.github.cjlee38.dumgen.scheme.Relation

interface QueryBuilder {
    fun build(relation: Relation, table: Table)
}
