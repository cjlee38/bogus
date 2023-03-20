package io.github.cjlee38.dumgen.scheme.type.parser

abstract class AbstractTypeParser : TypeParser {
    private val pattern = "(?<name>[a-z]+)[(]?(?<length>\\d*)[)]?"
    private val regex = Regex(pattern)

    protected abstract val knownTypes: List<String>
    override fun parsable(notation: String): Boolean {
        return knownTypes.any { notation.contains(it, ignoreCase = true) }
    }

    protected fun doSome(notation: String): List<String> {
        val matchResult = regex.find(notation) ?: throw IllegalArgumentException("invalid type : $notation")
        return matchResult.destructured.toList()
    }
}
