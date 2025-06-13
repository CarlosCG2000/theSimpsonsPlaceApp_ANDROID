package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesFilters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.models.Episode
import es.upsa.mimo.thesimpsonplace.domain.models.EpisodeFilter
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesByChapterUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesByDateUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesBySeasonUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesByTitleUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesByViewUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesOrderUseCase
import es.upsa.mimo.thesimpsonplace.utils.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListEpisodesFilterViewModel @Inject constructor(  val getEpisodesByTitleUseCase: GetEpisodesByTitleUseCase,
                                                        val getEpisodesByDateUseCase: GetEpisodesByDateUseCase,
                                                        val getEpisodesBySeasonUseCase: GetEpisodesBySeasonUseCase,
                                                        val getEpisodesByViewUseCase: GetEpisodesByViewUseCase,
                                                        val getEpisodesOrderUseCase: GetEpisodesOrderUseCase,
                                                        val getEpisodesByChapterUseCase: GetEpisodesByChapterUseCase,
                                                        /*private val savedStateHandle: SavedStateHandle*/): ViewModel(), Logger {

    private val _stateEpisode: MutableStateFlow<ListEpisodesFilterStateUI> = MutableStateFlow(ListEpisodesFilterStateUI())
    val stateEpisode: StateFlow<ListEpisodesFilterStateUI> = _stateEpisode.asStateFlow()
    private var allEpisodes: List<Episode> = emptyList()

    private val _episodeFilter = MutableStateFlow(EpisodeFilter())
    val episodeFilter = _episodeFilter.asStateFlow()
//  private val _episodeFilter: StateFlow<EpisodeFilter> = savedStateHandle.getStateFlow("filter", EpisodeFilter())
//  val episodeFilter = _episodeFilter

    fun updateField(update: (EpisodeFilter) -> EpisodeFilter) {
        _episodeFilter.value = update(_episodeFilter.value)
    }

    fun updateEpisodes(episodes: List<Episode>) {
        allEpisodes = episodes
        _stateEpisode.update { it.copy(episodes = episodes, isLoading = false) }
    }

    fun getEpisodesFilter(episode: EpisodeFilter){

        viewModelScope.launch {
            _stateEpisode.update { it.copy(isLoading = true) }

            var filteredEpisodes: List<Episode> = getEpisodesByTitleUseCase(episode.title.text, allEpisodes)

            if (filteredEpisodes.isNotEmpty())
                filteredEpisodes = getEpisodesByDateUseCase(episode.minDate, episode.maxDate, filteredEpisodes)

            if (filteredEpisodes.isNotEmpty())
                filteredEpisodes = getEpisodesBySeasonUseCase(episode.season, filteredEpisodes)

            if (filteredEpisodes.isNotEmpty())
                filteredEpisodes = getEpisodesByChapterUseCase(episode.episode, filteredEpisodes)
                                        /** solo quiero que se filtre cuando sea true el 'isView' */
            if (filteredEpisodes.isNotEmpty() && episode.isViewEnabled)
                filteredEpisodes = getEpisodesByViewUseCase(true, filteredEpisodes)

            _stateEpisode.update {
                it.copy(episodes = filteredEpisodes) // Orden inverso, isLoading = false)
            }

            getEpisodesOrder(episode.isOrderDesc)
            logInfo( "Cargando el número de episodios ${filteredEpisodes.size} por nombre ${episode.title.text} y otros filtros..." )
        }
    }

    private fun getEpisodesOrder(isAscendent: Boolean) {
        viewModelScope.launch {
            _stateEpisode.update { it.copy(isLoading = true) }
            val episodesOrder: List<Episode> = getEpisodesOrderUseCase(!isAscendent, _stateEpisode.value.episodes)

            _stateEpisode.update {
                it.copy(episodes = episodesOrder, isLoading = false)
            }
        }
    }
}