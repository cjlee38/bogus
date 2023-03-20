package io.github.cjlee38.dumgen.scheme

data class Relation(
    val name: String,
    val attributes: List<Attribute>
) {
    val fields: List<String>
        get() = attributes.map { it.field }
}
