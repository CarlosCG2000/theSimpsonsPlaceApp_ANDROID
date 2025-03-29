package es.upsa.mimo.thesimpsonplace.presentation.ui.root

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import es.upsa.mimo.thesimpsonplace.data.entities.user.Language
import es.upsa.mimo.thesimpsonplace.data.sources.database.UserDatastoreDao
import es.upsa.mimo.thesimpsonplace.domain.usescases.user.GetUserPreferencesUseCase
import es.upsa.mimo.thesimpsonplace.presentation.ui.navigation.NavegacionApp
import es.upsa.mimo.thesimpsonplace.presentation.ui.theme.TheSimpsonPlaceTheme
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.profile.ProfileViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

import java.util.Locale
import javax.inject.Inject


@AndroidEntryPoint // Para la inyección de dependecias con Hilt
class MainActivity() : ComponentActivity() {

    @Inject
    lateinit var getUserPreferencesUseCase: GetUserPreferencesUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("MainActivity", "Viendo el bundle: $savedInstanceState")

//        if (savedInstanceState == null) { // ✅ Evita ejecutar en recreaciones
//            lifecycleScope.launch {
//                val user = getUserPreferencesUseCase.userPreferencesFlow.firstOrNull()
//                user?.let {
//                    val newLocaleTag = when (it.language) {
//                        Language.SPANISH -> "es"
//                        Language.ENGLISH -> "en"
//                        Language.FRENCH -> "fr"
//                        else -> Locale.getDefault().toLanguageTag()
//                    }
//
//                    val currentLocaleTag = AppCompatDelegate.getApplicationLocales().toLanguageTags()
//
//                    if (currentLocaleTag != newLocaleTag) {
//                        Log.i("MainActivity", "Cambiando idioma a: $newLocaleTag")
//                        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(newLocaleTag))
//
//                        // 🔄 Reiniciar la actividad solo si el idioma cambia
//                        recreate()
//                    }
//                }
//           }
//        }

        setContent {
            MyApp() // NavegacionApp()
        }
    }

}

@Composable
fun MyApp(darkModeViewModel: ProfileViewModel = hiltViewModel()) {
    val userState = darkModeViewModel.userState.collectAsState()

    // Aplicar el idioma al contexto
    val context = LocalContext.current
    val resources = context.resources
    val configuration = resources.configuration

    LaunchedEffect(userState.value.user.language) {
        val locale = when (userState.value.user.language) {
            Language.SPANISH  -> Locale("es")
            Language.ENGLISH -> Locale("en")
            Language.FRENCH  -> Locale("fr")
            else -> Locale.getDefault()
        }

        configuration.setLocale(locale)

        resources.updateConfiguration(configuration, resources.displayMetrics)
//        AppCompatDelegate.setApplicationLocales(
//            LocaleListCompat.forLanguageTags(locale.language)
//        )
    }

    // MaterialTheme( colorScheme = if (userState.value.user.darkMode) darkColorScheme() else lightColorScheme()) {
    TheSimpsonPlaceTheme(darkTheme = userState.value.user.darkMode) {
        NavegacionApp()
    }
}

