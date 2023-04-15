package io.github.cjlee38.bogusapi.ui

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("hello")
    fun hello(): String {
        return "hello world"
    }
}