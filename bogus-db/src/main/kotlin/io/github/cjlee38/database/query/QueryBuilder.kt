package io.github.cjlee38.database.query

import io.github.cjlee38.database.Table

interface QueryBuilder {
    fun build(table: Table): List<String>
}
