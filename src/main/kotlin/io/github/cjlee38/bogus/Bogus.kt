package io.github.cjlee38.bogus

import io.github.cjlee38.bogus.generator.Table
import io.github.cjlee38.bogus.jdbc.QueryBuilder
import io.github.cjlee38.bogus.scheme.Relation
import io.github.cjlee38.bogus.scheme.SchemeAnalyzer
import org.springframework.stereotype.Component

@Component
class Bogus(
    private val schemeAnalyzer: SchemeAnalyzer,
    private val queryBuilder: QueryBuilder
) {

    fun run() {
        bootstrap()
        val schema = schemeAnalyzer.analyze()
        val associateWith = schema.generate()
        val map = associateWith.map {
            queryBuilder.build(it.key, it.value)
        }
    }

    private fun bootstrap() {
        // todo : read configuration file
    }
}
