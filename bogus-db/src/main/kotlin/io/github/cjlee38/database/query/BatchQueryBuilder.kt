package io.github.cjlee38.database.query

import io.github.cjlee38.database.DatabaseDetector
import io.github.cjlee38.database.data.Table
import mu.KotlinLogging
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Component
@Primary
class BatchQueryBuilder(
    private val databaseDetector: DatabaseDetector
) : QueryBuilder {
    private val logger = KotlinLogging.logger { }

    override fun build(table: Table): List<String> {
        val sb =
            StringBuilder("insert into ${databaseDetector.database}.${table.relation.name} (${table.relation.fields.joinToString()}) values ")
        sb.append(table.tuples.joinToString(", ") { "(${it.format().joinToString()})" })
        sb.append(";")
        logger.info { "query : $sb" }
        return listOf(sb.toString())
    }
}
