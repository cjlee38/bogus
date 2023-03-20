package io.github.cjlee38.dumgen.scheme

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class SchemeAnalyzer(
    private val dataSource: DataSource,
    private val jdbcTemplate: JdbcTemplate
) {
    fun analyze(): Schema {
        val tableNames = jdbcTemplate.query("show tables") { rs, rowNum ->
            rs.getString("Tables_in_dumgen") // todo : fix this
        }
        val relations: List<Relation> = tableNames.map {
            val attributes = jdbcTemplate.query("describe $it") { rs, rowNum ->
                val field = rs.getString("Field")
                val type = rs.getString("Type")
                val notNull = rs.getString("Null")
                val key = rs.getString("Key")
                val default = rs.getString("Default")
                val extra = rs.getString("Extra")

                Attribute(field, type, notNull, key, default, extra)
            }
            Relation(attributes)
        }
        return Schema(relations)
    }
}
