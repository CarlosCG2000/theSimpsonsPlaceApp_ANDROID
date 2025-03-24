package es.upsa.mimo.thesimpsonplace.presentation.ui.screens.episodeSection

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import es.upsa.mimo.thesimpsonplace.data.utils.Logger
import es.upsa.mimo.thesimpsonplace.data.utils.LoggerClass
import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.presentation.ui.components.BottomBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.components.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList.ListEpisodesStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList.ListEpisodesViewModel

@Composable
fun EpisodesScreen(
    viewModel: ListEpisodesViewModel = hiltViewModel(), // viewModel(factory = ListEpisodesViewModel.factory()),
    navigateToFilterEpisode: () -> Unit,
    navigateToFavoriteEpisode: () -> Unit,
    onEpisodeSelected: (String) -> Unit,
    navigationArrowBack:() -> Unit) {

    val state: State<ListEpisodesStateUI> = viewModel.episodesState.collectAsState() // pasa a ser sincrono para manejarlo en la UI

    // Crear instancia de Logger
    val logger = remember { LoggerClass() }

    LaunchedEffect(Unit) {
        viewModel.getAllEpisodes()
    }

    LaunchedEffect(state.value.episodes) {
        logger.logInfo("EpisodesScreen: ${state.value.episodes}")
    }

    Scaffold(
        bottomBar = {
                BottomBarComponent(
                    1,
                    { },
                    navigateToFilterEpisode,
                    navigateToFavoriteEpisode
                )
            },
        topBar = {
            TopBarComponent(
                title = "Listado de Episodios",
                onNavigationArrowBack = navigationArrowBack
            )
        }
    ) { paddingValues ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Red),
                contentAlignment = Alignment.Center) {

                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 60.dp), // Ocupa toda la pantalla
                    //verticalArrangement = Arrangement.Center, // Centra verticalmente dentro de Column
                   // horizontalAlignment = Alignment.CenterHorizontally // Centra horizontalmente
                    ){

                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        itemsIndexed(state.value.episodes) { index, item ->
                            EpisodeItem(index, item, onEpisodeSelected)
                        }
                    }

                }
            }
        }
}

@Composable
fun EpisodeItem(index:Int, episode: Episode, onEpisodeSelected: (String) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onEpisodeSelected(episode.id) // Ahora puedes obtener la posición del item
            }
    ) {

        Text(text = episode.titulo, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.width(16.dp))
        Row {
            Text(text = episode.lanzamiento.toString(), fontSize = 20.sp)
            Text(text = episode.temporada.toString(), fontSize = 16.sp)
            Text(text = episode.esVisto.toString(), fontSize = 16.sp)
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun EpisodesScreenPreview() {
    Column {
        EpisodesScreen(
            navigateToFilterEpisode = {},
            navigateToFavoriteEpisode = {},
            onEpisodeSelected = {},
            navigationArrowBack = {}
        )
    }
}
