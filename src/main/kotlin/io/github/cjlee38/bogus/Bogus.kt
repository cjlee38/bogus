package io.github.cjlee38.bogus

import io.github.cjlee38.bogus.dao.DataRepository
import io.github.cjlee38.bogus.generator.Column
import io.github.cjlee38.bogus.persistence.Storage
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
                    val column = Column(primaryAttribute, generatedIds)
                    Storage.save(column)
                }
            }
        }
    }
}
