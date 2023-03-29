package io.github.cjlee38.bogus.scheme.type.parser

import mu.KotlinLogging

abstract class AbstractTypeParser : TypeParser {

    private val logger = KotlinLogging.logger {}

    private val pattern = "(?<name>[a-z]+)[(]?(?<length>[0-9,]*)?[)]?(?<additional>[ ][a-z]+)?"
    private val regex = Regex(pattern)

    protected abstract val knownTypes: List<String>
    override fun parsable(notation: String): Boolean {
        return knownTypes.any { notation.contains(it, ignoreCase = true) }
    }

    protected fun destruct(notation: String): List<String> {
        logger.trace { notation }
        val matchResult = regex.find(notation) ?: throw IllegalArgumentException("invalid type : $notation")
        return matchResult.destructured.toList().map { it.trim() }
    }
}
