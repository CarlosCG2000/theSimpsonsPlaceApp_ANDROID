package es.upsa.mimo.thesimpsonplace.presentation.ui.screens.episodeSection

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import es.upsa.mimo.thesimpsonplace.presentation.ui.components.TopBarComponent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EpisodeDetailScreen(id: Int, navigationArrowBack:() -> Unit) {

    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Detalles del Episodio $id",
                onNavigationArrowBack = navigationArrowBack
            )
        }
    )
    { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Magenta).padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(), // Ocupa toda la pantalla
                verticalArrangement = Arrangement.Center, // Centra verticalmente dentro de Column
                horizontalAlignment = Alignment.CenterHorizontally
            ) { // Centra horizontalmente
                // LOGO SIMPSONS
                Text("Episodio en detalles con ID $id", fontSize = 24.sp, fontWeight = Bold)
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun EpisodeDetailScreenPreview() {
        EpisodeDetailScreen(1){}
}