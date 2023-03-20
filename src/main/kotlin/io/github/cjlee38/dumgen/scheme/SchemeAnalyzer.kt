package io.github.cjlee38.dumgen.scheme

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class SchemeAnalyzer(
    private val jdbcTemplate: JdbcTemplate
) {
    fun analyze() {
        val query: List<String> = jdbcTemplate.query("show tables") { rs, rowNum ->
            rs.getString("table_name")
        }
        println("query = ${query}")
    }
}
