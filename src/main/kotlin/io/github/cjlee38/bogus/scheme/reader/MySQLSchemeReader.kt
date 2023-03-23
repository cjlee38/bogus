package io.github.cjlee38.bogus.scheme.reader

import io.github.cjlee38.bogus.util.getLowerString
import io.github.cjlee38.bogus.util.getNullableLowerString
import mu.KotlinLogging
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class MySQLSchemeReader(
    private val jdbcTemplate: JdbcTemplate
) : SchemeReader {
    private val logger = KotlinLogging.logger { }

    override fun readDatabase(): String {
        val dataSource = jdbcTemplate.dataSource ?: throw IllegalStateException("datasource undefined")
        val database = dataSource.connection.catalog
        logger.info { "detected database : $database" }
        return database
    }

    override fun readTables(): List<String> {
        val tableNames = jdbcTemplate.query("show tables") { rs, _ ->
            rs.getString(1)
        }
        logger.info { "detected tables : $tableNames" }
        return tableNames
    }

    override fun readAttribute(table: String): List<AttributeResponse> {
        return jdbcTemplate.query("describe $table") { rs, _ ->
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

    override fun readReferences(database: String): List<ReferenceResponse> {
        val sql = "select * from information_schema.key_column_usage where table_schema = '$database';"

        return jdbcTemplate.query(sql) { rs, _ ->
            val name = rs.getLowerString("constraint_name")
            val relation = rs.getLowerString("table_name")
            val attribute = rs.getLowerString("column_name")
            val referencedDatabase: String? = rs.getNullableLowerString("referenced_table_schema")
            val referencedRelation = rs.getNullableLowerString("referenced_table_name")
            val referencedAttribute = rs.getNullableLowerString("referenced_column_name")

            ReferenceResponse(name, relation, attribute, referencedDatabase, referencedRelation, referencedAttribute)
        }
    }
}
