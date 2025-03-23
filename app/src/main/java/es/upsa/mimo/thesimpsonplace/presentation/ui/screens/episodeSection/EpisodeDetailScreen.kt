package es.upsa.mimo.thesimpsonplace.presentation.ui.screens.episodeSection

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import es.upsa.mimo.thesimpsonplace.presentation.ui.components.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodeDetails.DetailsEpisodeStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodeDetails.DetailsEpisodeViewModel

@Composable
fun EpisodeDetailScreen(id: String, navigationArrowBack:() -> Unit) {

    val viewModel: DetailsEpisodeViewModel = viewModel(factory = DetailsEpisodeViewModel.factory())
    val state: State<DetailsEpisodeStateUI> = viewModel.episodeState.collectAsState() // lo hago sincrono para usarlo en la pantalla

    LaunchedEffect(Unit) {
        viewModel.getEpisodeById(id)
    }

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
                Text("Titulo ${state.value.episode?.titulo}", fontSize = 24.sp, fontWeight = Bold)
                Text("descripcion ${state.value.episode?.descripcion}", fontSize = 24.sp, fontWeight = Bold)
                Text("temporada ${state.value.episode?.temporada}", fontSize = 24.sp, fontWeight = Bold)
                Text("esVisto ${state.value.episode?.esVisto}", fontSize = 24.sp, fontWeight = Bold)
                Text("esFavorito ${state.value.episode?.esFavorito}", fontSize = 24.sp, fontWeight = Bold)

                Text("escritores ${state.value.episode?.escritores}", fontSize = 24.sp, fontWeight = Bold)
                Text("directores ${state.value.episode?.directores}", fontSize = 24.sp, fontWeight = Bold)
                Text("invitados ${state.value.episode?.invitados}", fontSize = 24.sp, fontWeight = Bold)
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun EpisodeDetailScreenPreview() {
        EpisodeDetailScreen("1"){}
}