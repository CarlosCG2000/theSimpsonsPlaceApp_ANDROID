package es.upsa.mimo.thesimpsonplace.presentation.ui.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.data.entities.user.Language
import es.upsa.mimo.thesimpsonplace.presentation.ui.navigation.NavegacionApp
import es.upsa.mimo.thesimpsonplace.presentation.ui.theme.TheSimpsonPlaceTheme
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.profile.ProfileViewModel
import java.util.Locale

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

//        AppCompatDelegate.setApplicationLocales(
//            LocaleListCompat.forLanguageTags(locale.language)
//        )
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    // MaterialTheme( colorScheme = if (userState.value.user.darkMode) darkColorScheme() else lightColorScheme()) {
    TheSimpsonPlaceTheme(darkTheme = userState.value.user.darkMode) {
        NavegacionApp()
    }
}
