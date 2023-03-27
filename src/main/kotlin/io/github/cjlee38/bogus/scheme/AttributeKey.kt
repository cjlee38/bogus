package io.github.cjlee38.bogus.scheme

enum class AttributeKey {
    PRIMARY, UNIQUE, NONE;

    val isUnique: Boolean
        get() = this == PRIMARY || this == UNIQUE

    companion object {
        operator fun invoke(notation: String?): AttributeKey {
            return when (notation?.lowercase()) {
                "pri" -> PRIMARY
                "uni" -> UNIQUE
                else -> NONE
            }
        }
    }
}
