package io.github.cjlee38.bogus.jdbc

import io.github.cjlee38.bogus.generator.Table
import org.springframework.stereotype.Component

@Component
class EachQueryBuilder : QueryBuilder {
    override fun build(table: Table) {
        val sql = "insert into ${table.relation.name} (${table.relation.fields.joinToString()}) values (%s)"
        for (tuple in table.tuples) {
            val sql2 = sql.format(tuple.values.joinToString())
            println("sql2 = ${sql2}")
        }
    }
}
