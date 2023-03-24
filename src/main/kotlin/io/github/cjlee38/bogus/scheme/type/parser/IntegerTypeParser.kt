package io.github.cjlee38.bogus.scheme.type.parser

import io.github.cjlee38.bogus.scheme.type.DataType
import io.github.cjlee38.bogus.scheme.type.IntegerType
import org.springframework.stereotype.Component
import java.util.Locale

@Component
class IntegerTypeParser : AbstractTypeParser() {
    override val knownTypes = storageBytes.keys.toList()

    override fun parse(notation: String): DataType {
        val (name, _, unsigned) = super.destruct(notation)

        val (min, max) = getMinMax(name)
        return IntegerType(unsigned, min, max)
    }

    private fun getMinMax(name: String): Pair<Long, Long> {
        val bytes = storageBytes[name.lowercase(Locale.getDefault())] ?: throw IllegalArgumentException("invalid integer type : $name")
        val bits = bytes * byteBit
        val min = -1L shl (bits - 1)
        val max = (1L shl (bits - 1)) - 1
        return min to max
    }

    companion object {
        private const val byteBit = 8
        private val storageBytes = mapOf(
            "tinyint" to 1,
            "smallint" to 2,
            "mediumint" to 3,
            "int" to 4,
            "bigint" to 8
        )
    }
}
