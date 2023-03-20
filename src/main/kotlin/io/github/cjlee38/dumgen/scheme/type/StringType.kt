package io.github.cjlee38.dumgen.scheme.type

class StringType(
    private val isVariable: Boolean,
    private val length: Int
) : DType {
    companion object {
        private const val pattern = "(?<name>[a-z]*)\\((?<length>\\d+)\\)"
        private val regex = Regex(pattern)

        fun parse(notation: String): StringType {
            val matchResult = regex.find(notation) ?: throw IllegalArgumentException("invalid type")
            val (name, length) = matchResult.destructured.toList()
            if (name.equals("varchar", ignoreCase = true)) {
                return StringType(isVariable = true, length.toInt())
            }
            throw IllegalArgumentException()
        }
    }
}
