package io.github.cjlee38.database

interface DataRepository {
    fun saveTable(table: Table): List<Any>?
}
