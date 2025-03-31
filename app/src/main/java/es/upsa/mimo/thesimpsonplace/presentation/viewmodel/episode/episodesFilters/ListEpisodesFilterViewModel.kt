package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesFilters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.models.Episode
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesByChapterUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesByDateUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesBySeasonUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesByTitleUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesByViewUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesOrderUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ListEpisodesFilterViewModel @Inject constructor(  val getEpisodesByTitleUseCase: GetEpisodesByTitleUseCase,
                                                        val getEpisodesByDateUseCase: GetEpisodesByDateUseCase,
                                                        val getEpisodesBySeasonUseCase: GetEpisodesBySeasonUseCase,
                                                        val getEpisodesByViewUseCase: GetEpisodesByViewUseCase,
                                                        val getEpisodesOrderUseCase: GetEpisodesOrderUseCase,
                                                        val getEpisodesByChapterUseCase: GetEpisodesByChapterUseCase): ViewModel() {

    private val _stateEpisode: MutableStateFlow<ListEpisodesFilterStateUI> = MutableStateFlow(ListEpisodesFilterStateUI()) // Asincrono esta en un hilo secundario

    val stateEpisode: StateFlow<ListEpisodesFilterStateUI> = _stateEpisode.asStateFlow()

    private var allEpisodes: List<Episode> = emptyList() // 🔹 Lista completa de episodios
    private val defaultMinDate: Long = Calendar.getInstance().apply {set(1989, Calendar.DECEMBER, 17) }.timeInMillis

    fun updateEpisodes(episodes: List<Episode>) {
        allEpisodes = episodes
        _stateEpisode.update { it.copy(episodes = episodes, isLoading = false) } // ✅ Actualiza correctamente el estado
    }

    fun getEpisodesFilter(title: String = "", minDate: Date = Date(defaultMinDate), maxDate: Date = Date(), season: Int = 0, episode: Int = 0, isView: Boolean = false, order: Boolean = false){

        viewModelScope.launch {
            _stateEpisode.update { it.copy(isLoading = true) }

            // Comenzamos con la lista completa (allEpisodes)
            var filteredEpisodes: List<Episode> = getEpisodesByTitleUseCase(title, allEpisodes)
            if (filteredEpisodes.isNotEmpty()) filteredEpisodes = getEpisodesByDateUseCase(minDate, maxDate, filteredEpisodes)
            if (filteredEpisodes.isNotEmpty()) filteredEpisodes = getEpisodesBySeasonUseCase(season, filteredEpisodes)
            if (filteredEpisodes.isNotEmpty()) filteredEpisodes = getEpisodesByChapterUseCase(episode, filteredEpisodes)
                                        /** solo quiero que se filtre cuando sea true el 'isView' */
            if (filteredEpisodes.isNotEmpty() && isView) filteredEpisodes = getEpisodesByViewUseCase(true, filteredEpisodes)

            _stateEpisode.update {
                it.copy(episodes = filteredEpisodes, isLoading = false) // Orden inverso, isLoading = false)
            }

            getEpisodesOrder(order)
        }
    }

    fun getEpisodesOrder(isAscendent: Boolean) {
        viewModelScope.launch {
            _stateEpisode.update { it.copy(isLoading = true) }
            val episodesOrder: List<Episode> = getEpisodesOrderUseCase(!isAscendent, _stateEpisode.value.episodes)

            _stateEpisode.update {
                it.copy(episodes = episodesOrder, isLoading = false)
            }
        }
    }

}