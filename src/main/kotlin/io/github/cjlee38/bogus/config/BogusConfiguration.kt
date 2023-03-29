package io.github.cjlee38.bogus.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("bogus")
@ConstructorBinding
data class BogusConfiguration(
    val databaseName: String?,
    val analyzer: String?,
)

@ConfigurationProperties("database")
@ConstructorBinding
data class SchemaConfiguration(
    val relations: List<RelationConfiguration>
) {
    // todo : apply global configuration
    fun getAttributeConfiguration(relationName: String, attributeName: String): AttributeConfiguration {
        return (relations.find { it.name == relationName }
            ?.attributes?.find { it.name == attributeName }
            ?: AttributeConfiguration(attributeName))
    }

    fun getRelationConfiguration(relationName: String): RelationConfiguration {
        return relations.find { it.name == relationName }
            ?: RelationConfiguration(relationName, attributes = emptyList())
    }
}

data class RelationConfiguration(
    val name: String,
    val count: Int? = 1000,
    val attributes: List<AttributeConfiguration>
)

data class AttributeConfiguration(
    val name: String,
    val useAutoIncrement: Boolean? = true,
    val nullRatio: Double? = 0.1,
    val pattern: String? = "RANDOM"
)
