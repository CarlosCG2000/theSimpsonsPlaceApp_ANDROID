package es.upsa.mimo.thesimpsonplace.utils

import android.content.Context
import android.os.Build
import android.os.LocaleList
import java.util.Locale

// Se encarga del cambio de idioma de forma ordenada y eficiente.
object LocaleHelper {
    // ⚠️ Forma moderna de cambiar el idioma
    fun updateLocale(context: Context, language: String) {
        val locale = when (language) {
            "es" -> Locale("es")
            "en" -> Locale("en")
            "fr" -> Locale("fr")
            else -> Locale.getDefault()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // A partir de Android 13 (API 33), se debe usar LocaleManager para cambiar el idioma sin reiniciar la app:
            val localeManager = context.getSystemService(android.app.LocaleManager::class.java)
            localeManager?.applicationLocales = LocaleList.forLanguageTags(locale.toLanguageTag())
        } else {
            // Se usa la forma antigua (setLocale(locale)) con updateConfiguration, pero con suppress deprecation para evitar errores de compilación.
            @Suppress("DEPRECATION")
            val resources = context.resources
            val configuration = resources.configuration
            configuration.setLocale(locale)
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }
    }
}