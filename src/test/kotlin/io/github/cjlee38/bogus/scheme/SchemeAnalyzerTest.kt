package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.support.IntegrationTest

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
