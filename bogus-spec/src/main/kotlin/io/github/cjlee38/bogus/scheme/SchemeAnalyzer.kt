package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.config.AttributeConfiguration
import io.github.cjlee38.bogus.config.SchemaConfiguration
import io.github.cjlee38.bogus.scheme.pattern.Pattern
import io.github.cjlee38.database.reader.AttributeResponse
import io.github.cjlee38.database.reader.ReferenceResponse
import io.github.cjlee38.bogus.scheme.type.TypeInferrer
import io.github.cjlee38.database.SchemeRepository
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class SchemeAnalyzer(
    private val schemaConfiguration: SchemaConfiguration,
    private val schemeRepository: SchemeRepository,
    private val typeInferrer: TypeInferrer,
) {
    private val logger = KotlinLogging.logger {}

    fun analyze(): Schema {
        val references: List<ReferenceResponse> = schemeRepository.findReferences()
        val relations = schemeRepository.findTables()
            .map { relationName ->
                Relation(
                    relationName,
                    schemaConfiguration.getRelationConfiguration(relationName).count!!, // todo
                    schemeRepository.findAttributes(relationName)
                        .map { attributeResponse ->
                            toAttribute(
                                attributeResponse,
                                typeInferrer,
                                schemaConfiguration.getAttributeConfiguration(relationName, attributeResponse.field),
                            )
                        },
                )
            }
        return constructSchema(relations, references)
    }

    fun toAttribute(
        attributeResponse: AttributeResponse,
        typeInferrer: TypeInferrer,
        attributeConfiguration: AttributeConfiguration,
    ): Attribute {
        return Attribute(
            field = attributeResponse.field,
            type = typeInferrer.inferType(attributeResponse.type),
            pattern = Pattern.of(attributeConfiguration.pattern),
            constraints = createConstraints(
                attributeResponse.isNullable,
                attributeConfiguration.nullRatio,
                attributeResponse.default,
                attributeResponse.extra,
                attributeResponse.key
            ),
            isPrimary = attributeResponse.key == "pri"
        )
    }

    private fun createConstraints(
        isNullable: String,
        nullRatio: Double?,
        default: String?,
        extra: String?,
        key: String?
    ): Constraints {
        val values = mutableListOf<Constraint>()
        if (isNullable == "yes") values.add(NullableConstraint(nullRatio!!))
        if (default != null) values.add(DefaultConstraint())
        if (key == "pri" && extra == "auto_increment") values.add(AutoIncrementConstraint())
        if (key == "pri" || key == "uni") values.add(UniqueConstraint(null))
        return Constraints(values)
    }

    private fun constructSchema(relations: List<Relation>, references: List<ReferenceResponse>): Schema {
        applyReferences(references, relations)
        val sorted = topologySort(relations)
        logger.info { "after toplogical sort : $sorted" }
        return Schema(sorted, schemaConfiguration)
    }

    private fun applyReferences(referenceResponses: List<ReferenceResponse>, relations: List<Relation>) {
        referenceResponses.forEach { ref ->
            if (!ref.isForeign) {
                return@forEach
            }
            val relation = relations.first { it.name == ref.relation }
            val referencedRelation = relations.first { it.name == ref.referencedRelation }
            val attribute = relation.attributes.first { it.field == ref.attribute }
            val referencedAttribute = referencedRelation.attributes.first { it.field == ref.referencedAttribute }
            attribute.reference = Reference(relation, attribute, referencedRelation, referencedAttribute)
        }
    }

    private fun topologySort(relations: List<Relation>): List<Relation> {
        // initialize degree
        val inDegreeByRelation = relations
            .distinct()
            .associateWith { 0 }
            .toMutableMap()

        for (entry in inDegreeByRelation) {
            val references: List<Reference> = entry.key.attributes.map { it.reference }.filterNotNull()
            for (reference in references) {
                inDegreeByRelation[reference.referencedRelation] =
                    inDegreeByRelation[reference.referencedRelation]!! + 1
            }
        }

        // Q ready
        val Q = ArrayDeque<Relation>()
        for (entry in inDegreeByRelation) {
            if (entry.value == 0) {
                Q.addLast(entry.key)
            }
        }

        val sorted = mutableListOf<Relation>()
        while (Q.size != 0) {
            val current = Q.removeFirst()
            sorted.add(current)

            val refs = current.attributes.map { it.reference }.filterNotNull()
            for (ref in refs) {
                val refRelation = ref.referencedRelation
                inDegreeByRelation[refRelation] = inDegreeByRelation[refRelation]!! - 1
            }
            for (ref in refs) {
                val refRelation = ref.referencedRelation
                if (inDegreeByRelation[refRelation] == 0) {
                    Q.add(refRelation)
                }
            }
        }
        return sorted.reversed()
    }
}
