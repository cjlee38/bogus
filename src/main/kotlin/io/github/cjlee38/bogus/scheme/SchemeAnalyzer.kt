package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.util.getLowerString
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class SchemeAnalyzer(
    private val jdbcTemplate: JdbcTemplate,
    private val dataSource: DataSource,
    private val referenceAnalyzer: ReferenceAnalyzer,
) {
    fun analyze(): Schema {
        val database = requireDatabase()
        val references = referenceAnalyzer.analyze(database)
        val schema = requireTableNames()
            .map { Relation(it, requireAttribute(it)) }
            .let(::Schema)
        schema.applyReferences(references)
        return schema
    }

    private fun requireDatabase(): String {
        return dataSource.connection.catalog
    }

    private fun requireTableNames(): List<String> {
        return jdbcTemplate.query("show tables") { rs, _ ->
            rs.getString(1)
        }
    }

    private fun requireAttribute(it: String?): List<Attribute> {
        return jdbcTemplate.query("describe $it") { rs, _ ->
            val field = rs.getLowerString("Field")
            val type = rs.getLowerString("Type")
            val isNullable = rs.getLowerString("Null")
            val key = rs.getLowerString("Key")
            val default = rs.getLowerString("Default")
            val extra = rs.getLowerString("Extra")

            Attribute(field, type, isNullable, key, default, extra)
        }
    }
}
