package io.github.cjlee38.bogus.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("bogus")
@ConstructorBinding
data class BogusConfiguration(
    val databaseName: String?,
    val analyzer: String?,
)

@ConfigurationProperties("")
@ConstructorBinding
data class SchemaConfiguration(
    val database: Map<String, Map<String, AttributeConfiguration>>
) {
    fun getAttributeConfiguration(relationName: String, attributeName: String): AttributeConfiguration {
        val map = database[relationName] ?: return AttributeConfiguration()
        return map[attributeName] ?: return AttributeConfiguration()
    }
}

data class AttributeConfiguration(
    val useAutoIncrement: Boolean? = true,
    val nullRatio: Double? = 0.1,
    val pattern: String? = "RANDOM"
)
