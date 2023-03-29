package io.github.cjlee38.database.writer

import io.github.cjlee38.database.DataRepository
import io.github.cjlee38.database.data.Table
import io.github.cjlee38.database.query.QueryBuilder
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Component
import java.sql.Statement

@Component
class JdbcDataRepository(
    private val jdbcTemplate: JdbcTemplate,
    private val queryBuilder: QueryBuilder
) : DataRepository {
    override fun insertTable(table: Table): List<Any>? {
        val generatedKeyHolder = GeneratedKeyHolder()
        queryBuilder.build(table).forEach { query ->
            jdbcTemplate.update(
                { it.prepareStatement(query, Statement.RETURN_GENERATED_KEYS) },
                generatedKeyHolder
            )
        }
        return extractPrimary(table, generatedKeyHolder)
    }

    private fun extractPrimary(
        table: Table,
        generatedKeyHolder: GeneratedKeyHolder
    ): List<Any>? {
        return if (table.relation.primaryAttribute?.autoIncrement == true) {
            generatedKeyHolder.extractKeys()
        } else {
            table.columns.find { it.attribute.isPrimary }?.values
        }
    }

    private fun GeneratedKeyHolder.extractKeys(): List<Any> {
        return keyList.flatMap { it.values }
    }

}
