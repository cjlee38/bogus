package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.util.getLowerString
import io.github.cjlee38.bogus.util.getNullableLowerString
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class ReferenceAnalyzer(
    private val jdbcTemplate: JdbcTemplate
) {
    fun analyze(database: String): List<ReferenceInfo> {
        val sql = "select * from information_schema.key_column_usage where table_schema = '$database';"
        return jdbcTemplate.query(sql) { rs, _ ->
            val name = rs.getLowerString("constraint_name")
            val relation = rs.getLowerString("table_name")
            val attribute = rs.getLowerString("column_name")
            val referencedDatabase: String? = rs.getNullableLowerString("referenced_table_schema")
            val referencedRelation = rs.getNullableLowerString("referenced_table_name")
            val referencedAttribute = rs.getNullableLowerString("referenced_column_name")

            ReferenceInfo(name, relation, attribute, referencedDatabase, referencedRelation, referencedAttribute)
        }
    }
}
