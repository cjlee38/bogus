package io.github.cjlee38.bogus

import io.github.cjlee38.database.DataRepository
import io.github.cjlee38.database.data.Column
import io.github.cjlee38.bogus.scheme.SchemeAnalyzer
import org.springframework.stereotype.Component

@Component
class Bogus(
    private val schemeAnalyzer: SchemeAnalyzer,
    private val dataRepository: DataRepository,
) {
    fun run() {
        val schema = schemeAnalyzer.analyze()
        val it = schema.iterate()
        while (it.hasNext()) {
            val table = it.next()
            val generatedIds = dataRepository.insertTable(table)
            if (generatedIds != null) {
                val primaryAttribute = table.relation.primaryAttribute
                if (primaryAttribute != null) {
                    primaryAttribute.column = Column(primaryAttribute, generatedIds)
                }
            }
        }
    }
}
