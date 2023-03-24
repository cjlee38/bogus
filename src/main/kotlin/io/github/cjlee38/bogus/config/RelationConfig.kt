package io.github.cjlee38.bogus.config

data class RelationConfig(
    val count: Int = 5,
    val useAutoIncrement: Boolean = true,
    val attributeConfigs: List<AttributeConfig> = mutableListOf()
)
