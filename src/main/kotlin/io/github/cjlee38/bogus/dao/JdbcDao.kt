package io.github.cjlee38.bogus.dao

import io.github.cjlee38.bogus.generator.Table
import io.github.cjlee38.bogus.jdbc.QueryBuilder
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Component
import java.sql.Statement

@Component
class JdbcDao(
    private val jdbcTemplate: JdbcTemplate,
    private val queryBuilder: QueryBuilder
) : Dao {
    override fun insertTable(table: Table): List<Any?>? {
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
    ): List<Any?>? {
        val useAutoIncrement = true
        return if (useAutoIncrement) {
            generatedKeyHolder.extractKeys()
        } else {
            table.columns.find { it.attribute.isPrimary }?.values
        }
    }
}

private fun GeneratedKeyHolder.extractKeys(): List<Any> {
    return keyList.flatMap { it.values }
}
