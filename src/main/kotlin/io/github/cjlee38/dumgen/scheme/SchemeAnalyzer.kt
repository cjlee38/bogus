package io.github.cjlee38.dumgen.scheme

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class SchemeAnalyzer(
    private val jdbcTemplate: JdbcTemplate
) {
    fun analyze(): Schema {
        return requireTableNames()
            .map { Relation(requireAttribute(it)) }
            .let(::Schema)
    }

    private fun requireTableNames(): List<String> {
        return jdbcTemplate.query("show tables") { rs, _ ->
            rs.getString("Tables_in_dumgen") // todo : fix this
        }
    }

    private fun requireAttribute(it: String?): List<Attribute> {
        return jdbcTemplate.query("describe $it") { rs, _ ->
            val field = rs.getString("Field")
            val type = rs.getString("Type")
            val isNullable = rs.getString("Null")
            val key = rs.getString("Key")
            val default = rs.getString("Default")
            val extra = rs.getString("Extra")

            Attribute(field, type, isNullable, key, default, extra)
        }
    }
}
