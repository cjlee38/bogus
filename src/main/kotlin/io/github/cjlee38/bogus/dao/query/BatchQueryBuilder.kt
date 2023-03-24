package io.github.cjlee38.bogus.dao.query

import io.github.cjlee38.bogus.generator.Table
import mu.KotlinLogging
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Component
@Primary
class BatchQueryBuilder : QueryBuilder {
    private val logger = KotlinLogging.logger {  }

    override fun build(table: Table): List<String> {
        val sb = StringBuilder("insert into ${table.relation.name} (${table.relation.fields.joinToString()}) values ")
        sb.append(table.tuples.joinToString(", ") { "(${it.format().joinToString()})" })
        sb.append(";")
        logger.info { "query : $sb" }
        return listOf(sb.toString())
    }
}
