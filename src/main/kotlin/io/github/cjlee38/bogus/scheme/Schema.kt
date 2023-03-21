package io.github.cjlee38.bogus.scheme

data class Schema(val relations: List<Relation>) {
    fun applyReferences(referenceInfos: List<ReferenceInfo>) {
        referenceInfos.forEach { ref ->
            if (!ref.isForeign) {
                return@forEach
            }
            println(ref)
            val relation = relations.first { it.name == ref.relation }
            val referencedRelation = relations.first { it.name == ref.referencedRelation }
            val attribute = relation.attributes.first { it.field == ref.attribute }
            val referencedAttribute = referencedRelation.attributes.first { it.field == ref.referencedAttribute }
            attribute.reference = Reference(attribute, referencedAttribute)
        }
    }
}
