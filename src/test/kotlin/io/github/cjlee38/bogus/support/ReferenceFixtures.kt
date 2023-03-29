package io.github.cjlee38.bogus.support

import io.github.cjlee38.bogus.scheme.Attribute
import io.github.cjlee38.bogus.scheme.Reference

fun createReference(
    attribute: Attribute = createAttribute(),
    referencedAttribute: Attribute = createAttribute()
): Reference {
    return Reference(attribute.relation, attribute, referencedAttribute.relation, referencedAttribute)
}
