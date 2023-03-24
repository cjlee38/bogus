package io.github.cjlee38.bogus.scheme.pattern

interface Pattern {

}

enum class StringPattern: Pattern {
    RANDOM, UUID, REGEX
}

enum class NumberPattern: Pattern {
    RANDOM, SEQUENTIAL
}
