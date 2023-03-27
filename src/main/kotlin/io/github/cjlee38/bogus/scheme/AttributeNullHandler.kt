package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.scheme.type.Default
import io.github.cjlee38.bogus.scheme.type.Null
import io.github.cjlee38.bogus.util.mixIn
import kotlin.random.Random

class AttributeNullHandler(
    val isNullable: Boolean,
    val nullRatio: Double,
    val default: Any
) {
    constructor(isNullable: String, nullRatio: Double?, default: String?) : this(
        isNullable == "yes",
        nullRatio ?: 0.1,
        default ?: Null
    )

    fun handle(generate: () -> Any?): () -> Any? {
        return generate.mixIn {
            if (Random.nextDouble(0.0, 1.0) <= nullRatio) {
                if (isNullable) Null else Default ?: it()
            }
            else it()
        }
    }

    companion object {
        val NOT_NULL = AttributeNullHandler(false, 0.0, Null)
    }
}
