package io.github.cjlee38.bogus.scheme

data class ReferenceInfo(
    val name: String,
    val relation: String,
    val attribute: String,
    val referencedDatabase: String?,
    val referencedRelation: String?,
    val referencedAttribute: String?
) {
    val isForeign: Boolean
        get() = (name != "primary") or (!referencedAttribute.isNullOrBlank())
}
