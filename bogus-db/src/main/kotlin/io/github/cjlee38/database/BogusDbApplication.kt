package io.github.cjlee38.database

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BogusDbApplication

fun main(args: Array<String>) {
    runApplication<BogusDbApplication>(*args)
}
