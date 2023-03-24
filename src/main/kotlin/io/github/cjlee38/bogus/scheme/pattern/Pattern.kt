package io.github.cjlee38.bogus.scheme.pattern

interface Pattern {
    companion object {
        val RANDOM = object : Pattern {}

        fun of(pattern: String?): Pattern {
            if (pattern == null || pattern.equals("RANDOM", ignoreCase = true)) {
                return RANDOM
            }
            return (StringPattern.values().find { it.name.equals(pattern, ignoreCase = true) }
                ?: NumberPattern.values().find { it.name.equals(pattern, ignoreCase = true) })
                ?: throw IllegalArgumentException()
        }


    }
}

enum class StringPattern : Pattern {
    UUID, REGEX
}

enum class NumberPattern : Pattern {
    SEQUENTIAL
}
