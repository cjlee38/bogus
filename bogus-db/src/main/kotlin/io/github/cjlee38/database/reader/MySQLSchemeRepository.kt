package io.github.cjlee38.bogus.scheme.reader

import io.github.cjlee38.bogus.util.getLowerString
import io.github.cjlee38.bogus.util.getNullableLowerString
import io.github.cjlee38.database.DatabaseDetector
import io.github.cjlee38.database.SchemeRepository
import mu.KotlinLogging
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class MySQLSchemeRepository(
    private val jdbcTemplate: JdbcTemplate,
    databaseDetector: DatabaseDetector,
) : SchemeRepository {
    private val logger = KotlinLogging.logger { }

    private lateinit var database: String

    init {
        this.database = databaseDetector.database
    }

    override fun findTables(): List<String> {
        val tableNames = jdbcTemplate.query("show tables from $database") { rs, _ ->
            rs.getString(1)
        }
        logger.info { "detected tables : $tableNames" }
        return tableNames
    }

    override fun findAttributes(table: String): List<AttributeResponse> {
        return jdbcTemplate.query("describe ${database}.$table") { rs, _ ->
            AttributeResponse(
                rs.getLowerString("Field"),
                rs.getLowerString("Type"),
                rs.getLowerString("Null"),
                rs.getNullableLowerString("Key"),
                rs.getNullableLowerString("Default"),
                rs.getNullableLowerString("Extra")
            )
        }
    }

    override fun findReferences(): List<ReferenceResponse> {
        val sql = "select * from information_schema.key_column_usage where table_schema = '${database}';"

        return jdbcTemplate.query(sql) { rs, _ ->
            val name = rs.getLowerString("constraint_name")
            val relation = rs.getLowerString("table_name")
            val attribute = rs.getLowerString("column_name")
            val referencedRelation = rs.getNullableLowerString("referenced_table_name")
            val referencedAttribute = rs.getNullableLowerString("referenced_column_name")

            ReferenceResponse(name, relation, attribute, referencedRelation, referencedAttribute)
        }
    }
}
