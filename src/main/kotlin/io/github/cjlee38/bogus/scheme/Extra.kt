package io.github.cjlee38.bogus.scheme

data class Extra(
   val autoIncrement: Boolean
) {
    companion object {
        operator fun invoke(notation: String?): Extra {
            return Extra(
                notation?.contains("auto_increment") == true
            )
        }
    }
}
