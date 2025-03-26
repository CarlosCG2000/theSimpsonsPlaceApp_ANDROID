package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav

import android.util.Log
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
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ListEpisodesFilterViewModel @Inject constructor(  val getEpisodesByTitleUseCase: GetEpisodesByTitleUseCase,
                                                        val getEpisodesByDateUseCase: GetEpisodesByDateUseCase,
                                                        val getEpisodesBySeasonUseCase: GetEpisodesBySeasonUseCase,
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
            var filteredEpisodes: List<Episode> = getEpisodesByTitleUseCase.execute(title, allEpisodes)
            if (filteredEpisodes.isNotEmpty()) filteredEpisodes = getEpisodesByDateUseCase.execute(minDate, maxDate, filteredEpisodes)

             if (filteredEpisodes.isNotEmpty()) {
                Log.i("getEpisodesFilter", season.toString())
                Log.i("getEpisodesFilter",  "Antes del filtro $filteredEpisodes")
                filteredEpisodes = getEpisodesBySeasonUseCase.execute(season, filteredEpisodes)
                Log.i("getEpisodesFilter",  "Despues del filtro $filteredEpisodes")
            }

            if (filteredEpisodes.isNotEmpty()) filteredEpisodes = getEpisodesByChapterUseCase.execute(episode, filteredEpisodes)

// FALTA FILTRO DE SI ESTA VISTO O NO EL EPISODIO (AÑADIRLO EN TODOS LADOS, CASOS DE USO, REPOSITORIO, ETC) Y RECOTAR CUANDO SE A SEASON Y EPISODE 0 SE LLAME A TODOS
// ...
            _stateEpisode.update {
                it.copy(episodes = filteredEpisodes, isLoading = false) // Orden inverso, isLoading = false)
            }

//            if (order){
//                _stateEpisode.update {
//                    it.copy(episodes = filteredEpisodes.sortedBy { it.lanzamiento }, isLoading = false) // Orden inverso, isLoading = false)
//                }
//            } else {
//                _stateEpisode.update {
//                    it.copy(episodes = filteredEpisodes.sortedByDescending { it.lanzamiento }, isLoading = false) // Orden inverso, isLoading = false)
//                }
//            }
        }

    }

}