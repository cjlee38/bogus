package io.github.cjlee38.bogus.dao.query

import io.github.cjlee38.bogus.generator.Table
import org.springframework.stereotype.Component

@Component
class EachQueryBuilder : QueryBuilder {
    override fun build(table: Table): List<String> {
        val sql = "insert into ${table.relation.name} (${table.relation.fields.joinToString()}) values (%s);"
        return table.tuples.map { sql.format(it.values.joinToString()) }
    }
}
