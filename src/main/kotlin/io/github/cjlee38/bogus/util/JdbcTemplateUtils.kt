package io.github.cjlee38.bogus.util

import java.sql.ResultSet

fun ResultSet.getLowerString(columnLabel: String): String {
    return getString(columnLabel).lowercase()
}

fun ResultSet.getNullableLowerString(columnLabel: String): String? {
    return getString(columnLabel)?.lowercase()
}
