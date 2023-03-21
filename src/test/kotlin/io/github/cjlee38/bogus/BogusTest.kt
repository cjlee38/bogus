package io.github.cjlee38.bogus

import io.github.cjlee38.bogus.support.IntegrationTest
import io.kotest.core.spec.style.StringSpec

@IntegrationTest
class BogusTest(
    private val bogus: Bogus
) : StringSpec({
    "run" {
        bogus.run()
    }
})
