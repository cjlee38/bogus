package io.github.cjlee38.bogus.jdbc

import io.github.cjlee38.bogus.generator.Table

interface QueryBuilder {
    fun build(table: Table)
}
