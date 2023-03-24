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
) {
    fun getAttributeConfiguration(relationName: String, attributeName: String): AttributeConfiguration {
        val map = relations[relationName] ?: return AttributeConfiguration()
        return map[attributeName] ?: return AttributeConfiguration()
    }
}

data class AttributeConfiguration(
    val useAutoIncrement: Boolean? = true,
    val nullRatio: Double? = 0.1,
    val pattern: String? = "RANDOM"
)
