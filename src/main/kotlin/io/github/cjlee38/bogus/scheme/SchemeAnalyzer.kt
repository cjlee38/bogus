package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.scheme.type.TypeInferrer
import io.github.cjlee38.bogus.util.getLowerString
import io.github.cjlee38.bogus.util.getNullableLowerString
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class SchemeAnalyzer(
    private val jdbcTemplate: JdbcTemplate,
    private val typeInferrer: TypeInferrer,
    private val dataSource: DataSource,
    private val referenceAnalyzer: ReferenceAnalyzer,
) {
    fun analyze(): Schema {
        val database = requireDatabase()
        val references = referenceAnalyzer.analyze(database)
        val relations = requireTableNames()
            .map { Relation(it, requireAttribute(it)) }
        return Schema(post(references, relations))
    }

    private fun post(references: List<ReferenceInfo>, relations: List<Relation>): List<Relation> {
        applyReferences(references, relations)
        return topologySort(relations)
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

        for (entry in inDegreeByAttribute) {
            val attribute = entry.key
            val ref = attribute.reference
            if (ref != null) {
                inDegreeByAttribute[ref.referencedAttribute] = inDegreeByAttribute[ref.referencedAttribute]!! + 1
                inDegreeByRelation[ref.referencedRelation] = inDegreeByRelation[ref.referencedRelation]!! + 1
            }
        }
        // Q ready
        val Q = ArrayDeque<Attribute>()
        for (entry in inDegreeByAttribute) {
            if (entry.value == 0) {
                Q.addLast(entry.key)
            }
        }

        val sorted = mutableListOf<Relation>()
        while (Q.size != 0) {
            val current = Q.removeFirst()
            // attribute
            val ref = current.reference
            if (ref != null) {
                val refAttribute = ref.referencedAttribute
                inDegreeByAttribute[refAttribute] = inDegreeByAttribute[refAttribute]!! - 1
                if (inDegreeByAttribute[refAttribute] == 0) {
                    Q.addLast(refAttribute)
                }

                val refRelation = ref.referencedRelation
                inDegreeByRelation[refRelation] = inDegreeByRelation[refRelation]!! - 1
            }
            // relation

            if (inDegreeByRelation[current.relation] == 0) {
                sorted.add(current.relation)
            }
        }
        return sorted.reversed()
    }

    private fun requireDatabase(): String {
        return dataSource.connection.catalog
    }

    private fun requireTableNames(): List<String> {
        return jdbcTemplate.query("show tables") { rs, _ ->
            rs.getString(1)
        }
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
