package io.github.cjlee38.bogus.jdbc

import io.github.cjlee38.bogus.generator.Table
import io.github.cjlee38.bogus.scheme.Relation

class BatchQueryBuilder : QueryBuilder {
    override fun build(relation: Relation, table: Table) {
        val sb = StringBuilder("insert into ${relation.name} (${relation.fields.joinToString()}) values ")
        val values = table.tuples.joinToString(", ") { "(${it.values.joinToString()})" }
        sb.append(values)
        sb.append(";")
        println(sb.toString())
    }
}
