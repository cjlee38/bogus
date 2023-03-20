package io.github.cjlee38.dumgen.scheme

import io.github.cjlee38.dumgen.support.IntegrationTest

import org.junit.jupiter.api.Test

@IntegrationTest
class SchemeAnalyzerTest(
    private val schemeAnalyzer: SchemeAnalyzer
) {
    @Test
    fun asd() {
        val schema = schemeAnalyzer.analyze()
        println(schema)
    }
}
