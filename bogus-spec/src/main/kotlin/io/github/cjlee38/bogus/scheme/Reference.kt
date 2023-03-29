package io.github.cjlee38.bogus.scheme

data class Reference(
    val relation: Relation,
    val attribute: Attribute,
    val referencedRelation: Relation,
    val referencedAttribute: Attribute,
)
