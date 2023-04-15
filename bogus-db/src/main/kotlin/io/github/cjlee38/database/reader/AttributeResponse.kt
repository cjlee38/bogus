package io.github.cjlee38.database.reader

data class AttributeResponse(
    val field: String,
    val type: String,
    val isNullable: String,
    val key: String?,
    val default: String?,
    val extra: String?
)
