package io.github.cjlee38.bogus

import io.github.cjlee38.database.DataRepository
import io.github.cjlee38.database.Column
import io.github.cjlee38.bogus.scheme.SchemeAnalyzer
import org.springframework.stereotype.Component

@Component
class Bogus(
    private val schemeAnalyzer: SchemeAnalyzer,
    private val dataRepository: DataRepository,
) {
    fun run() {
        val schema = schemeAnalyzer.analyze()
        schema.relations.forEach {
            val table = it.generateTable()
            val generatedIds = dataRepository.saveTable(table)
            if (generatedIds != null) {
                val primaryAttribute = it.primaryAttribute
                if (primaryAttribute != null) {
                    primaryAttribute.column = Column(primaryAttribute.field, true, generatedIds.map { it.toString() })
                }
            }
        }
//        val it = schema.iterate()
//        while (it.hasNext()) {
//            val table = it.next()
//            val generatedIds = dataRepository.saveTable(table)
//            if (generatedIds != null) {
//                val primaryAttribute = table.primary
//                if (primaryAttribute != null) {
//                    primaryAttribute.column = Column(primaryAttribute, generatedIds)
//                }
//            }
//        }
    }
}
