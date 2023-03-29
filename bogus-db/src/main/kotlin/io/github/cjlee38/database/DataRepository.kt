package io.github.cjlee38.database

import io.github.cjlee38.database.data.Table

interface DataRepository {
    fun insertTable(table: Table): List<Any>?
}
