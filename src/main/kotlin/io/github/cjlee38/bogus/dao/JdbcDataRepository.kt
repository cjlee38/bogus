package io.github.cjlee38.bogus.dao

import io.github.cjlee38.bogus.generator.Table
import io.github.cjlee38.bogus.dao.query.QueryBuilder
import io.github.cjlee38.bogus.scheme.AttributeKey
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Component
import java.sql.Statement

@Component
class JdbcDataRepository(
    private val jdbcTemplate: JdbcTemplate,
    private val queryBuilder: QueryBuilder
) : DataRepository {
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
        val useAutoIncrement = true // todo : temporary
        val autoIncrement = table.relation.primaryAttribute?.extra?.autoIncrement == true
        return if (autoIncrement && useAutoIncrement) {
            generatedKeyHolder.extractKeys()
        } else {
            table.columns.find { it.attribute.key == AttributeKey.PRIMARY }?.values
        }
    }
}

private fun GeneratedKeyHolder.extractKeys(): List<Any> {
    return keyList.flatMap { it.values }
}
