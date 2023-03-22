package io.github.cjlee38.bogus

import io.github.cjlee38.bogus.config.UserConfiguration
import io.github.cjlee38.bogus.dao.Dao
import io.github.cjlee38.bogus.generator.Column
import io.github.cjlee38.bogus.persistence.Storage
import io.github.cjlee38.bogus.scheme.SchemeAnalyzer
import org.springframework.stereotype.Component

@Component
class Bogus(
    private val schemeAnalyzer: SchemeAnalyzer,
    private val dao: Dao,
) {

    fun run() {
        bootstrap()
        val schema = schemeAnalyzer.analyze()
        val it = schema.iterate()
        while (it.hasNext()) {
            val table = it.next()
            val generatedIds = dao.insertTable(table)
            if (generatedIds != null) {
                val primaryAttribute = table.relation.primaryAttribute
                if (primaryAttribute != null) {
                    val column = Column(primaryAttribute, generatedIds)
                    Storage.save(column)
                }
            }
        }
    }

    private fun bootstrap() {
        UserConfiguration
        // todo : read configuration file
    }
}
