package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.episodeSection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.BottomBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.BottomNavItem
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList.ListEpisodesStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList.ListEpisodesViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav.ListEpisodesDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav.ListEpisodesDbStateUI

@Composable
fun EpisodesFavScreen(
    viewModelAllEpisodes: ListEpisodesViewModel = hiltViewModel(),
    viewModelDB: ListEpisodesDBViewModel = hiltViewModel(),
    navigateToAllEpisodes: () -> Unit,
    navigateToFilterEpisode: () -> Unit,
    onEpisodeSelected: (String) -> Unit,
    navigationArrowBack:() -> Unit
) {
    var stateAllEpisodes: State<ListEpisodesStateUI> = viewModelAllEpisodes.episodesState.collectAsState()
    val stateFavOrView: State<ListEpisodesDbStateUI> = viewModelDB.stateEpisodesFavOrView.collectAsState()

    LaunchedEffect(Unit) {
        viewModelAllEpisodes.getAllEpisodes() // cargue todos los episodios de primeras
    }

    Scaffold(
        bottomBar = {
            BottomBarComponent(
                BottomNavItem.FAVORITES,
                navigateToAllEpisodes,
                navigateToFilterEpisode
            ) { }
        },
        topBar = {
            TopBarComponent(
                title = "Listado de Episodios Fav",
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
            if (stateFavOrView.value.isLoading) {
                CircularProgressIndicator(
                    color = Color.Yellow // ✅ Cambia el color del spinner a amarillo
                )
            } else {
                ListEpisodes(modifier = Modifier.fillMaxSize(),
                    episodes = stateFavOrView.value.episodesFav,
                    allEpisodes = stateAllEpisodes.value.episodes,
                    onEpisodeSelected = onEpisodeSelected,
                    episodesFavDbSet = stateFavOrView.value.episodesFavSet,
                    episodesViewDbSet = stateFavOrView.value.episodesViewSet)
            }
        }
    }
}

//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
//@Composable
//fun EpisodesFavScreenPreview() {
//    Column {
//        EpisodesFavScreen( {}, {},{},{})
//    }
//}