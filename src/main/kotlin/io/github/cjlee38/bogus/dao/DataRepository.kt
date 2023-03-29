package io.github.cjlee38.bogus.dao

import io.github.cjlee38.bogus.generator.Table

interface DataRepository {
    fun insertTable(table: Table): List<Any>?
}
