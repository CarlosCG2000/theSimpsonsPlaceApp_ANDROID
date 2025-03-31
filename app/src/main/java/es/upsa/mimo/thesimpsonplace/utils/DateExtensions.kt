package es.upsa.mimo.thesimpsonplace.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun String.toDate(): Date? {
    return try {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        format.parse(this)
    } catch (e: Exception) {
        throw e
    }
}

fun Date.toFormattedString(): String {
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(this)
}