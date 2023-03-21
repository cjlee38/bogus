package io.github.cjlee38.bogus.jdbc

import io.github.cjlee38.bogus.generator.Table
import io.github.cjlee38.bogus.scheme.Relation
import org.springframework.stereotype.Component

@Component
class EachQueryBuilder : QueryBuilder {
    override fun build(relation: Relation, table: Table) {
        val sql = "insert into ${relation.name} (${relation.fields.joinToString()}) values (%s)"
        for (tuple in table.tuples) {
            val sql2 = sql.format(tuple.values.joinToString())
            println("sql2 = ${sql2}")
        }
    }
}
