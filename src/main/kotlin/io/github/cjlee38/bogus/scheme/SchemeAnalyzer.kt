package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.scheme.type.TypeInferrer
import io.github.cjlee38.bogus.util.getLowerString
import io.github.cjlee38.bogus.util.getNullableLowerString
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class SchemeAnalyzer(
    private val jdbcTemplate: JdbcTemplate,
    private val typeInferrer: TypeInferrer,
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
            val key = rs.getNullableLowerString("Key")
            val default = rs.getNullableLowerString("Default")
            val extra = rs.getNullableLowerString("Extra")

            Attribute(field, type, isNullable, key, default, extra)
        }
    }
}
