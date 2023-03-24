package io.github.cjlee38.bogus.scheme.reader

interface SchemeRepository {
    fun getDatabase(): String
    fun readTables(): List<String>
    fun findAttributes(table: String): List<AttributeResponse>
    fun readReferences(database: String): List<ReferenceResponse>
}