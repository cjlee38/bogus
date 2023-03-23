package io.github.cjlee38.bogus.scheme.reader

interface SchemeReader {
    fun readDatabase(): String
    fun readTables(): List<String>
    fun readAttribute(table: String): List<AttributeResponse>
    fun readReferences(database: String): List<ReferenceResponse>
}
