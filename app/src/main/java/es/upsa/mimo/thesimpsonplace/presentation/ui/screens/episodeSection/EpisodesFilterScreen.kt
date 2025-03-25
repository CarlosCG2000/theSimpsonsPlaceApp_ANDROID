package es.upsa.mimo.thesimpsonplace.presentation.ui.screens.episodeSection

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.presentation.ui.components.BottomBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.components.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersList.ListCharactersViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList.ListEpisodesStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList.ListEpisodesViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav.ListEpisodesFilterStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav.ListEpisodesFilterViewModel

@Composable
fun EpisodesFilterScreen(viewModelAllEpisodes: ListEpisodesViewModel = hiltViewModel(),
                         viewModel: ListEpisodesFilterViewModel = hiltViewModel(),
                         navigateToAllEpisodes: () -> Unit,
                         navigateToFavoriteEpisode: () -> Unit,
                         onEpisodeSelected: (String) -> Unit,
                         navigationArrowBack:() -> Unit) {

    var stateAllEpisodes: State<ListEpisodesStateUI> = viewModelAllEpisodes.episodesState.collectAsState()
    var state: State<ListEpisodesFilterStateUI> = viewModel.stateEpisode.collectAsState() // pasa a ser sincrono para manejarlo en la UI

    LaunchedEffect(Unit) {
        viewModelAllEpisodes.getAllEpisodes() // cargue todos los episodios de primeras
        // state.value.episodes = stateAllEpisodes.value.episodes
        // viewModel.updateEpisodes(stateAllEpisodes.value.episodes) // se lo pase al a variable del ViewModel de los filtros
    }

    LaunchedEffect(stateAllEpisodes.value.episodes) {
        viewModel.updateEpisodes(stateAllEpisodes.value.episodes)// cargue todos los episodios de primeras
    }

    Scaffold(
        bottomBar = {
            BottomBarComponent(
                2,
                navigateToAllEpisodes,
                { },
                navigateToFavoriteEpisode
            )
        },
        topBar = {
            TopBarComponent(
                title = "Listado de Episodios Fav",
                onNavigationArrowBack = navigationArrowBack
            )
        }
    ) { paddingValues ->

        ConstraintLayout(
            // contentAlignment = Alignment.Center,
            modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues))
            //.background(Color.Blue))
        {

            val (xxxx, yyyy) = createRefs()

            // DEFINIR TODOS LOS FILTROS
            // ...

            if(state.value.isLoading || stateAllEpisodes.value.isLoading){
                CircularProgressIndicator(
                    color = Color.Yellow,
                    modifier = Modifier.constrainAs(xxxx) {
                        centerTo(parent)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                )
            } else {
                ListEpisodes( Modifier.constrainAs(yyyy) {
                                                centerTo(parent)
                                                start.linkTo(parent.start)
                                                end.linkTo(parent.end)
                                                         },
                                state.value.episodes,
                                onEpisodeSelected)
            }

        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun EpisodesFilterScreenPreview() {
    Column {
        EpisodesFilterScreen( navigateToAllEpisodes = {},
                              navigateToFavoriteEpisode = {},
                              onEpisodeSelected= {},
                              navigationArrowBack = {})
    }
}