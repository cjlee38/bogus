package io.github.cjlee38.bogus.config

import io.github.cjlee38.bogus.support.IntegrationTest
import org.junit.jupiter.api.Test

@IntegrationTest
class UserConfigurationTest(
    private val userConfiguration: UserConfiguration
) {
    @Test
    fun asd() {
        println(userConfiguration)
    }
}
