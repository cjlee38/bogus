package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.scheme.type.TypeInferrer
import io.github.cjlee38.bogus.util.getLowerString
import io.github.cjlee38.bogus.util.getNullableLowerString
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import javax.sql.DataSource
import mu.KotlinLogging

@Component
class SchemeAnalyzer(
    private val jdbcTemplate: JdbcTemplate,
    private val typeInferrer: TypeInferrer,
    private val dataSource: DataSource,
    private val referenceAnalyzer: ReferenceAnalyzer,
) {
    private val logger = KotlinLogging.logger {}

    fun analyze(): Schema {
        val database = requireDatabase()
        val references = referenceAnalyzer.analyze(database)
        val relations = requireTableNames()
            .map { Relation(it, requireAttribute(it)) }
        return Schema(post(references, relations))
    }

    private fun post(references: List<ReferenceInfo>, relations: List<Relation>): List<Relation> {
        applyReferences(references, relations)
        val sorted = topologySort(relations)
        logger.info { "after toplogical sort : $sorted" }
        return sorted
    }

    private fun applyReferences(referenceInfos: List<ReferenceInfo>, relations: List<Relation>) {
        referenceInfos.forEach { ref ->
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
        val inDegreeByAttribute = relations
            .flatMap { relation -> relation.attributes.map { it.reference } }
            .filterNotNull()
            .flatMap { listOf(it.attribute, it.referencedAttribute) }
            .associateWith { 0 }
            .toMutableMap()

        val inDegreeByRelation = inDegreeByAttribute.keys
            .map { it.relation }
            .distinct()
            .associateWith { 0 }
            .toMutableMap()

        for (entry in inDegreeByRelation) {
            val references: List<Reference> = entry.key.attributes.map { it.reference }.filterNotNull()
            for (reference in references) {
                inDegreeByRelation[reference.referencedRelation] = inDegreeByRelation[reference.referencedRelation]!! + 1
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

    private fun requireDatabase(): String {
        val database = dataSource.connection.catalog
        logger.info { "detected database : $database" }
        return database
    }

    private fun requireTableNames(): List<String> {
        val tableNames = jdbcTemplate.query("show tables") { rs, _ ->
            rs.getString(1)
        }
        logger.info { "detected tables : $tableNames" }
        return tableNames
    }

    private fun requireAttribute(it: String?): List<Attribute> {
        return jdbcTemplate.query("describe $it") { rs, _ ->
            val field = rs.getLowerString("Field")
            val type = rs.getLowerString("Type")
            val isNullable = rs.getLowerString("Null")
            val key = rs.getNullableLowerString("Key")
            val default = rs.getNullableLowerString("Default")
            val extra = rs.getNullableLowerString("Extra")

            Attribute(
                field = field,
                type = typeInferrer.inferType(type),
                isNullable = isNullable == "yes",
                key = key,
                default = default,
                extra = extra
            )
        }
    }
}
