package io.github.cjlee38.dumgen.scheme

import io.github.cjlee38.dumgen.IntegrationTest
import org.assertj.core.api.Assertions.*

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@IntegrationTest
class SchemeAnalyzerTest(
    private val schemeAnalyzer: SchemeAnalyzer
) {
    @Test
    fun asd() {
        schemeAnalyzer.analyze()
    }
}
