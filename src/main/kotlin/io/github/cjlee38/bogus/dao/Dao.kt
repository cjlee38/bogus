package io.github.cjlee38.bogus.dao

import io.github.cjlee38.bogus.generator.Table

interface Dao {
    fun insertTable(table: Table): List<Any?>?
}
