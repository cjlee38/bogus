package io.github.cjlee38.bogus.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("bogus")
@ConstructorBinding
data class UserConfiguration(
    val databaseName: String?,
    val analyzer: String?,
    val useAutoIncrement: Boolean?,
    val relations: Map<String, Map<String, AttributeConfiguration>>
)

data class AttributeConfiguration(
    val useAutoIncrement: Boolean?,
    val nullRatio: Double?,
    val pattern: String?
)
