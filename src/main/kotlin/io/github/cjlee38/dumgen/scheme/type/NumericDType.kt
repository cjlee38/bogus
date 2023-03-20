package io.github.cjlee38.dumgen.scheme.type

class NumericDType(
    private val isUnsigned: Boolean,
    private val min: Long,
    private val max: Long
) : DType {
    companion object {
        private const val pattern = "(?<name>[a-z]*)\\((?<zfill>\\d+)\\)"
        private val regex = Regex(pattern)

        fun parse(notation: String): NumericDType {
            val matchResult = regex.find(notation) ?: throw IllegalArgumentException("invalid type")
            val (name, zfill) = matchResult.destructured.toList()
            if (name.equals("bigint", ignoreCase = true)) {
                val min = -1L shl 16
                val max = (1L shl 16) - 1
                return NumericDType(false, min, max)
            }
            throw IllegalArgumentException()
        }
    }
}
