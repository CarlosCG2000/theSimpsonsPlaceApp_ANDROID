package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.episodeSection

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.utils.LoggerClass
import es.upsa.mimo.thesimpsonplace.domain.models.Episode
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.BottomBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.BottomNavItem
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList.ListEpisodesStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList.ListEpisodesViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav.ListEpisodesDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav.ListEpisodesDbStateUI
import es.upsa.mimo.thesimpsonplace.utils.toFormattedString
import kotlin.Int

@Composable
fun EpisodesScreen(
    viewModel: ListEpisodesViewModel = hiltViewModel(), // viewModel(factory = ListEpisodesViewModel.factory()),
    viewModelDB: ListEpisodesDBViewModel = hiltViewModel(),
    navigateToFilterEpisode: () -> Unit,
    navigateToFavoriteEpisode: () -> Unit,
    onEpisodeSelected: (String) -> Unit,
    navigationArrowBack:() -> Unit) {

    val state: State<ListEpisodesStateUI> = viewModel.episodesState.collectAsState() // pasa a ser sincrono para manejarlo en la UI
    val stateFavOrView: State<ListEpisodesDbStateUI> = viewModelDB.stateEpisodesFavOrView.collectAsState()

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
                    BottomNavItem.ALL,
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

            Box(
            contentAlignment = Alignment.Center, // ✅ Asegura que el spinner esté centrado
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                // .background(Color.Primary) // ✅ Fondo blanco para mejor visibilidad,
        ) {
            if (state.value.isLoading && stateFavOrView.value.isLoading) {
                CircularProgressIndicator(
                    color = Color.Yellow // ✅ Cambia el color del spinner a amarillo
                )
            } else {
                ListEpisodes(modifier = Modifier.fillMaxSize(),
                             episodes = state.value.episodes,
                             onEpisodeSelected = onEpisodeSelected,
                             episodesFavDbSet = stateFavOrView.value.episodesFavSet,
                             episodesViewDbSet = stateFavOrView.value.episodesViewSet)
            }
        }
    }
}

@Composable
fun ListEpisodes(modifier: Modifier,
                 episodes: List<Episode>,
                 allEpisodes: List<Episode> = episodes,
                 onEpisodeSelected: (String) -> Unit,
                 episodesFavDbSet: Set<String>,
                 episodesViewDbSet: Set<String>) {
    Column(
        modifier = modifier, // Ocupa toda la pantalla
        //verticalArrangement = Arrangement.Center, // Centra verticalmente dentro de Column
        // horizontalAlignment = Alignment.CenterHorizontally // Centra horizontalmente
    ) {

        LazyColumn(modifier = Modifier.fillMaxSize()) {

            items(episodes) {episode ->

                val indiceEpisodio = allEpisodes.indexOfFirst { it.id == episode.id }.takeIf { it != -1 } ?: -1

                val isFavorite = rememberUpdatedState(episode.id in episodesFavDbSet)
                val isView = rememberUpdatedState(episode.id in episodesViewDbSet)

                EpisodeItem(indiceEpisodio, episode, onEpisodeSelected, isFavorite.value, isView.value)
            }

        }

    }
}

@Composable
fun EpisodeItem(indiceEpisodio: Int, episode: Episode, onEpisodeSelected: (String) -> Unit, isFavorite: Boolean, isView: Boolean) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor =
                                    if (isFavorite) Color.Gray else Color(0xFF2C3E72) ), // Azul oscuro o Gris
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    onEpisodeSelected(episode.id) // Ahora puedes obtener la posición del item
                }// .background()
        ) {
            Text(text = "${indiceEpisodio + 1} - ${episode.titulo}", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Row {
                Text(text = episode.lanzamiento.toFormattedString(), fontSize = 20.sp)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Temporada ${ episode.temporada }", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = if (isView) Icons.Filled.Visibility else Icons.Filled.VisibilityOff, // Usa el ícono de estrella
                    contentDescription = "View",
                    tint = if (isView) Color.Yellow else Color.Transparent, // Amarillo si es visto, rojo si no
                    modifier = Modifier.size(38.dp) // Tamaño del icono
                )
            }
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
