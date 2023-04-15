package io.github.cjlee38.database

import io.github.cjlee38.database.reader.AttributeResponse
import io.github.cjlee38.database.reader.ReferenceResponse

interface SchemeRepository {
    fun findTables(): List<String>
    fun findAttributes(table: String): List<AttributeResponse>
    fun findReferences(): List<ReferenceResponse>
}
