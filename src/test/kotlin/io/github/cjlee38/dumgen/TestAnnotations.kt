package io.github.cjlee38.dumgen

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@Retention
@Target(allowedTargets = [AnnotationTarget.CLASS])
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
annotation class IntegrationTest
