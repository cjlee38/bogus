package io.github.cjlee38.bogus.scheme.reader

data class ReferenceResponse(
    val name: String,
    val relation: String,
    val attribute: String,
    val referencedRelation: String?,
    val referencedAttribute: String?
) {
    val isForeign: Boolean
        get() = (name != "primary") or (!referencedAttribute.isNullOrBlank())
}
