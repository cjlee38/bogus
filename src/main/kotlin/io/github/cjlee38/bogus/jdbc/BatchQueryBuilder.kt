package io.github.cjlee38.bogus.jdbc

import io.github.cjlee38.bogus.generator.Table
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Component
@Primary
class BatchQueryBuilder : QueryBuilder {
    override fun build(table: Table) {
        val sb = StringBuilder("insert into ${table.relation.name} (${table.relation.fields.joinToString()}) values ")
        val values = table.tuples.joinToString(", ") { "(${it.values.joinToString()})" }
        sb.append(values)
        sb.append(";")
        println(sb.toString())
    }
}
