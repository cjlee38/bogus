package io.github.cjlee38.bogus.scheme

enum class AttributeKey {
    PRIMARY, NONE;

    companion object {
        operator fun invoke(notation: String?): AttributeKey {
            if (notation == "pri") {
                return PRIMARY
            }
            return NONE
        }
    }
}
