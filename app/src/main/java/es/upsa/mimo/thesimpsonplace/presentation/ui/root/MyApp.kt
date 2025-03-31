package es.upsa.mimo.thesimpsonplace.presentation.ui.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.presentation.ui.navigation.NavegacionApp
import es.upsa.mimo.thesimpsonplace.presentation.ui.theme.TheSimpsonPlaceTheme
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.profile.ProfileViewModel
import es.upsa.mimo.thesimpsonplace.utils.LocaleHelper

@Composable
fun MyApp(darkModeViewModel: ProfileViewModel = hiltViewModel()) {
    val userState = darkModeViewModel.userState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(userState.value.user.language) {
        LocaleHelper.updateLocale(context, userState.value.user.language.code)
    }

    TheSimpsonPlaceTheme(darkTheme = userState.value.user.darkMode) {
        NavegacionApp()
    }
}
