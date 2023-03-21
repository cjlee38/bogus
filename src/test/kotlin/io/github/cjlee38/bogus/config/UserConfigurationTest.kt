package io.github.cjlee38.bogus.config

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class UserConfigurationTest : StringSpec({

    "init" {
        UserConfiguration
    }

    "get" {
        val cnf = UserConfiguration.get("test")
        cnf shouldBe "true"
    }

    "get nested" {
        val cnf = UserConfiguration.get("bogus/relations/team/use_auto_increment")
        cnf shouldBe "false"
    }

    "get leaf" {
        val cnf = UserConfiguration.get("bogus/relations/team/name/null_ratio")
        cnf shouldBe "0.1"
    }
})
