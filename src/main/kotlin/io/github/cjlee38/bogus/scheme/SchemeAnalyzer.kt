package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.config.UserConfiguration
import io.github.cjlee38.bogus.scheme.reader.ReferenceResponse
import io.github.cjlee38.bogus.scheme.reader.SchemeRepository
import io.github.cjlee38.bogus.scheme.type.TypeInferrer
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class SchemeAnalyzer(
    private val userConfiguration: UserConfiguration,
    private val schemeRepository: SchemeRepository,
    private val typeInferrer: TypeInferrer,
) {
    private val logger = KotlinLogging.logger {}

    fun analyze(): Schema {
        val relations = schemeRepository.readTables()
            .map { relationName ->
                Relation(
                    relationName,
                    schemeRepository.findAttributes(relationName)
                        .map {
                            val attributeConfiguration =
                                userConfiguration.getAttributeConfiguration(relationName, it.field)
                            it.toAttribute(typeInferrer, attributeConfiguration)
                        }
                )
            }
        val references = schemeRepository.readReferences()
        return constructSchema(relations, references)
    }

    private fun constructSchema(relations: List<Relation>, references: List<ReferenceResponse>): Schema {
        applyReferences(references, relations)
        val sorted = topologySort(relations)
        logger.info { "after toplogical sort : $sorted" }
        return Schema(sorted, userConfiguration)
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
