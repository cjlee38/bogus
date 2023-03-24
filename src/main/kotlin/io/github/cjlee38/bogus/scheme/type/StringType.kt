package io.github.cjlee38.bogus.scheme.type

import io.github.cjlee38.bogus.scheme.pattern.Pattern
import io.github.cjlee38.bogus.scheme.pattern.StringPattern
import org.apache.commons.lang3.RandomStringUtils
import java.util.UUID

data class StringType(
    val isVariable: Boolean,
    val length: Int
) : DataType<String> {
    override fun generate(pattern: Pattern): String {
        if (pattern == StringPattern.UUID) {
            return UUID.randomUUID().toString()
        }
        if (isVariable) {
            return RandomStringUtils.randomAlphanumeric(0, length)
        }
        return RandomStringUtils.randomAlphanumeric(length)
        // todo : regex
    }
}
