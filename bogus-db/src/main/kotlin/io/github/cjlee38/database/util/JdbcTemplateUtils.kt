package io.github.cjlee38.database.util

import java.sql.ResultSet

fun ResultSet.getLowerString(columnLabel: String): String {
    return getString(columnLabel).lowercase()
}

fun ResultSet.getNullableLowerString(columnLabel: String): String? {
    val value = getString(columnLabel)?.lowercase()
    return if (value.isEffectiveNull()) null else value
}

fun String?.isEffectiveNull(): Boolean {
    return this.isNullOrEmpty() || this.equals("null", ignoreCase = true)
}
