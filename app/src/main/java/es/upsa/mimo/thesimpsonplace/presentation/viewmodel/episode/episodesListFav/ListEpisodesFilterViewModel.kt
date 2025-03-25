package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetAllCharactersUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesByChapterUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesByDateUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesBySeasonUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesByTitleUseCase
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersList.ListCharactersStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList.ListEpisodesViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ListEpisodesFilterViewModel @Inject constructor(  val getEpisodesByTitleUseCase: GetEpisodesByTitleUseCase,
                                                        val getEpisodesByDateUseCase: GetEpisodesByDateUseCase,
                                                        val getEpisodesBySeasonUseCase: GetEpisodesBySeasonUseCase,
                                                        val getEpisodesByChapterUseCase: GetEpisodesByChapterUseCase): ViewModel() {

    private val _stateEpisode: MutableStateFlow<ListEpisodesFilterStateUI> = MutableStateFlow(ListEpisodesFilterStateUI()) // Asincrono esta en un hilo secundario

    val stateEpisode: StateFlow<ListEpisodesFilterStateUI> = _stateEpisode.asStateFlow()

    fun updateEpisodes(episodes: List<Episode>) {
        _stateEpisode.update { it.copy(episodes = episodes, isLoading = false) } // ✅ Actualiza correctamente el estado
    }

    // Hay que llamar a los casos de uso
    fun getEpisodesByTitle(title:String) {
        viewModelScope.launch {

            _stateEpisode.update { it.copy(isLoading = true) }

            val episodesAnt = _stateEpisode.value.episodes

            val episodesList = getEpisodesByTitleUseCase.execute(title, episodesAnt) // ✅ Obtiene los personajes

            _stateEpisode.update {
                it.copy(episodes = episodesList, isLoading = false)
            }
        }
    }

    fun getEpisodesByDate(minDate: Date, maxDate: Date) {
        viewModelScope.launch {

            _stateEpisode.update { it.copy(isLoading = true) }

            val episodesAnt = _stateEpisode.value.episodes

            val episodesList = getEpisodesByDateUseCase.execute(minDate, maxDate, episodesAnt) // ✅ Obtiene los personajes

            _stateEpisode.update {
                it.copy(episodes = episodesList, isLoading = false)
            }
        }
    }

    fun getEpisodesBySeason(season: Int) {
        viewModelScope.launch {

            _stateEpisode.update { it.copy(isLoading = true) }

            val episodesAnt = _stateEpisode.value.episodes

            val episodesList = getEpisodesBySeasonUseCase.execute(season, episodesAnt) // ✅ Obtiene los personajes

            _stateEpisode.update {
                it.copy(episodes = episodesList, isLoading = false)
            }
        }
    }

    fun getEpisodesByChapter(episode: Int) {
        viewModelScope.launch {

            _stateEpisode.update { it.copy(isLoading = true) }

            val episodesAnt = _stateEpisode.value.episodes

            val episodesList = getEpisodesByChapterUseCase.execute(episode, episodesAnt) // ✅ Obtiene los personajes

            _stateEpisode.update {
                it.copy(episodes = episodesList, isLoading = false)
            }
        }
    }
}