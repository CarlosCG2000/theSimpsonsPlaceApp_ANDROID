package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.episodeSection

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.R
import es.upsa.mimo.thesimpsonplace.utils.LoggerClass
import es.upsa.mimo.thesimpsonplace.domain.models.Episode
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.BottomBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.BottomNavItem
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.ModifierContainer
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.episode.ListEpisodes
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

    val logger = remember { LoggerClass() }     // Crear instancia de Logger, para seguridad

    LaunchedEffect(Unit) {
        if (state.value.episodes.isEmpty())
            viewModel.getAllEpisodes()
    }

    Scaffold(
        bottomBar = {
                BottomBarComponent(
                    selectedBarButtom = BottomNavItem.ALL,
                    { /** es esta pantalla, no necesita navegar */ },
                    navigateToFilterEpisode,
                    navigateToFavoriteEpisode
                )
            },
        topBar = {
            TopBarComponent(
                title = stringResource(R.string.todos_los_episodios),
                onNavigationArrowBack = navigationArrowBack
            )
        }
    ) { paddingValues ->
        if (state.value.isLoading && stateFavOrView.value.isLoading) {
            Box(
                modifier = ModifierContainer(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        else {
            ListEpisodes(modifier = ModifierContainer(paddingValues)
                                            .padding(top = 10.dp),
                         episodes = state.value.episodes,
                         allEpisodes = state.value.episodes,
                         onEpisodeSelected = onEpisodeSelected,
                         episodesFavDbSet = stateFavOrView.value.episodesFavSet,
                         episodesViewDbSet = stateFavOrView.value.episodesViewSet)
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
