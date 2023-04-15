package io.github.cjlee38.bogusapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BogusApiApplication

fun main(args: Array<String>) {
    runApplication<BogusApiApplication>(*args)
}
