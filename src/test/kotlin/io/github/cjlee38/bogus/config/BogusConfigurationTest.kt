package io.github.cjlee38.bogus.config

import io.github.cjlee38.bogus.support.IntegrationTest
import org.junit.jupiter.api.Test

@IntegrationTest
class BogusConfigurationTest(
    private val bogusConfiguration: BogusConfiguration,
    private val schemaConfiguration: SchemaConfiguration
) {
    @Test
    fun initialize() {
        println(bogusConfiguration)
        println(schemaConfiguration)
    }
}
