package io.github.cjlee38.bogus

import io.github.cjlee38.bogus.config.UserConfiguration
import io.github.cjlee38.bogus.jdbc.QueryBuilder
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
        val data = schema.generate()
        data.forEach { queryBuilder.build(it) }
    }

    private fun bootstrap() {
        UserConfiguration
        // todo : read configuration file
    }
}
