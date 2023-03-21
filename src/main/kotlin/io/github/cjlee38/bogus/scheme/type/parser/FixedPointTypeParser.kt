package io.github.cjlee38.bogus.scheme.type.parser

import io.github.cjlee38.bogus.scheme.type.DType
import io.github.cjlee38.bogus.scheme.type.FixedPointType
import org.springframework.stereotype.Component

@Component
class FixedPointTypeParser: AbstractTypeParser() {
    override val knownTypes = listOf("decimal")

    override fun parse(notation: String): DType {
        val (_, accuracy ,_) = destruct(notation)
        val (precision, scale) = accuracy.split(",")
        return FixedPointType(precision, scale)
    }
}
