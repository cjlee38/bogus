package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.util.mixIn
import kotlin.random.Random

class AttributeNullHandler(
    private val isNullable: Boolean,
    private val nullRatio: Double,
    private val default: Any?
) {
    constructor(isNullable: String, nullRatio: Double?, default: String?) : this(
        isNullable == "yes",
        nullRatio ?: 0.1,
        default
    )

    fun handle(generate: () -> Any?): () -> Any? {
        val nullValue = if (isNullable) null else default
        return generate.mixIn { if (Random.nextDouble(0.0, 1.0) <= nullRatio) nullValue else it() }
    }

    companion object {
        val NOT_NULL = AttributeNullHandler(false, 0.0, null)
    }
}
