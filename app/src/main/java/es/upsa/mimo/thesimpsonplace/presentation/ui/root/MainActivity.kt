package es.upsa.mimo.thesimpsonplace.presentation.ui.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import es.upsa.mimo.thesimpsonplace.presentation.ui.navigation.NavegacionApp
import es.upsa.mimo.thesimpsonplace.presentation.ui.theme.TheSimpsonPlaceTheme
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.profile.ProfileViewModel

@AndroidEntryPoint // Para la inyección de dependecias con Hilt
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            TheSimpsonPlaceTheme {
                MyApp()
                // NavegacionApp()
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
            }
        }
    }
}

@Composable
fun MyApp(darkModeViewModel: ProfileViewModel = hiltViewModel()) {
    val userState = darkModeViewModel.userState.collectAsState()

    // MaterialTheme( colorScheme = if (userState.value.user.darkMode) darkColorScheme() else lightColorScheme()) {
    TheSimpsonPlaceTheme(darkTheme = userState.value.user.darkMode) {
        NavegacionApp()
    }
}