package io.github.cjlee38.bogus

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BogusApplication

fun main(args: Array<String>) {
    runApplication<BogusApplication>(*args)
}
