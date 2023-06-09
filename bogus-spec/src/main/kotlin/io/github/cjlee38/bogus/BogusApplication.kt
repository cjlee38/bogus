package io.github.cjlee38.bogus

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan
class BogusApplication

fun main(args: Array<String>) {
    runApplication<BogusApplication>(*args)
}
