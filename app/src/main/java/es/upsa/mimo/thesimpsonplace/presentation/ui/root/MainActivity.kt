package es.upsa.mimo.thesimpsonplace.presentation.ui.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import es.upsa.mimo.thesimpsonplace.presentation.ui.navigation.NavegacionApp
import es.upsa.mimo.thesimpsonplace.presentation.ui.theme.TheSimpsonPlaceTheme

@AndroidEntryPoint // Para la inyección de dependecias con Hilt
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            TheSimpsonPlaceTheme {
                NavegacionApp()
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