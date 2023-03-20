package io.github.cjlee38.dumgen

import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.annotation.Commit
import org.springframework.test.context.TestConstructor

@JdbcTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Commit
class Tester(
    private val jdbcTemplate: JdbcTemplate
) {
    @Test
    fun default() {
        jdbcTemplate.execute("insert into team (name, sub_name) values ('hello', 'world')")
        val query = jdbcTemplate.queryForObject("select * from team") { rs, rowNum ->
            rs.getString("name")
        }
        println("query = ${query}")
    }

    @Test
    fun show_tables() {
        val res = jdbcTemplate.queryForList("show tables")
        println("res = ${res}")
    }
}
