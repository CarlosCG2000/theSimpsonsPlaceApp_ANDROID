package es.upsa.mimo.thesimpsonplace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import es.upsa.mimo.thesimpsonplace.presentation.ui.root.MyApp

@AndroidEntryPoint // Para la inyección de dependecias con Hilt
class MainActivity() : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

/**
        if (savedInstanceState == null) { // ✅ Evita ejecutar en recreaciones
            lifecycleScope.launch {
                val user = getUserPreferencesUseCase.userPreferencesFlow.firstOrNull()
                user?.let {
                    val newLocaleTag = when (it.language) {
                        Language.SPANISH -> "es"
                        Language.ENGLISH -> "en"
                        Language.FRENCH -> "fr"
                        else -> Locale.getDefault().toLanguageTag()
                    }

                    val currentLocaleTag = AppCompatDelegate.getApplicationLocales().toLanguageTags()

                    if (currentLocaleTag != newLocaleTag) {
                        Log.i("MainActivity", "Cambiando idioma a: $newLocaleTag")
                        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(newLocaleTag))

                        // 🔄 Reiniciar la actividad solo si el idioma cambia
                        recreate()
                    }
                }
           }
        }
*/

        setContent {
            MyApp()
        }
    }

}


