package io.github.cjlee38.dumgen

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DumgenApplication

fun main(args: Array<String>) {
    runApplication<DumgenApplication>(*args)
}
