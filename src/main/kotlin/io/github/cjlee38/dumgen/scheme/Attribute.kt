package io.github.cjlee38.dumgen.scheme

data class Attribute(
    private val field: String?,
    private val type: String?,
    private val notNull: String?,
    private val key: String?,
    private val default: String?,
    private val extra: String?
)
