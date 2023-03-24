package io.github.cjlee38.bogus.dao.query

import io.github.cjlee38.bogus.generator.Table

interface QueryBuilder {
    fun build(table: Table): List<String>
}
