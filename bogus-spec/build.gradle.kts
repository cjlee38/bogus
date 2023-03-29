import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.9"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

repositories {
    mavenCentral()
}

extra["kotlin-coroutines.version"] = "1.6.0"

dependencies {
    implementation("org.apache.commons:commons-lang3:3.12.0")
    api(project(":bogus-db"))
}
