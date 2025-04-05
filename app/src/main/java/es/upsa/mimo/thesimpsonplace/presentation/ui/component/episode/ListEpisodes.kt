package es.upsa.mimo.thesimpsonplace.presentation.ui.component.episode

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.upsa.mimo.thesimpsonplace.domain.models.Episode

@Composable
fun ListEpisodes(modifier: Modifier,
                 episodes: List<Episode>,
                 allEpisodes: List<Episode>, // para sacar solo el indice de los episodios
                 onEpisodeSelected: (String) -> Unit,
                 episodesFavDbSet: Set<String>,
                 episodesViewDbSet: Set<String>,
                 listState: LazyListState = LazyListState()) {

    LazyColumn(modifier = modifier, state = listState) {

        items(episodes, key = { it.id }) {episode ->
            val indiceEpisodio = allEpisodes.indexOfFirst { it.id == episode.id }.takeIf { it != -1 } ?: -1

            // val isFavorite = rememberUpdatedState(episode.id in episodesFavDbSet)
            // val isView = rememberUpdatedState(episode.id in episodesViewDbSet)
            val isFavorite = episode.id in episodesFavDbSet
            val isView = episode.id in episodesViewDbSet

            EpisodeItem(Modifier
                .fillMaxWidth()
                .padding(16.dp),
                indiceEpisodio,
                episode,
                onEpisodeSelected,
                isFavorite, //.value,
                isView //.value
            )
        }
    }
}

