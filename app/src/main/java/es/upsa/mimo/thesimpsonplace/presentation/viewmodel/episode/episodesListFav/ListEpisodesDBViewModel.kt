package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetAllEpisodesDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodeDbByIdUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetWatchedEpisodesUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.InsertEpisodeDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.IsEpisodeDbFavoriteUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.IsEpisodeDbWatchedUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.UpdateEpisodeDbStatusUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListEpisodesDBViewModel @Inject constructor(
    private val getAllEpisodesDbUseCase: GetAllEpisodesDbUseCase,
    private val getEpisodeDbByIdUseCase: GetEpisodeDbByIdUseCase,
//    private val getWatchedEpisodesUseCase: GetWatchedEpisodesUseCase, // NO SE NECESITA AQUÍ, PERO SE UTILIZAR EN OTRO CASO DE USO DE FILTROS DE EPISODIOS POR VIEWS
//    private val isEpisodeDbWatchedUseCase: IsEpisodeDbWatchedUseCase, // NO SE NECESITA
//    private val isEpisodeDbFavoriteUseCase: IsEpisodeDbFavoriteUseCase, // NO SE NECECITA
    private val insertEpisodeDbUseCase: InsertEpisodeDbUseCase,
    private val updateEpisodeDbStatusUseCase: UpdateEpisodeDbStatusUseCase
) : ViewModel() {

    private val _stateEpisodesFavOrView = MutableStateFlow<ListEpisodesDbStateUI>(ListEpisodesDbStateUI())
    val stateEpisodesFavOrView: StateFlow<ListEpisodesDbStateUI> = _stateEpisodesFavOrView.asStateFlow()

    init {
        loadFavoritesOrViews()
    }

    // 🔹 Cargar personajes y marcar favoritos
    private fun loadFavoritesOrViews() {
        viewModelScope.launch {
            getAllEpisodesDbUseCase.execute().collect { episodesList ->

                _stateEpisodesFavOrView.update {
                    it.copy( episodes = episodesList,
                             episodesView = episodesList.filter { it.esVisto },
                             episodesFav = episodesList.filter { it.esFavorito },
                             episodesSet = episodesList.mapNotNull { it.id }.toSet(),  // todos los personajes de la BD que son favoritos (en este caso siempre van a ser todos)
                            episodesViewSet = episodesList.mapNotNull {
                               if (it.esVisto) it.id else null
                           }.toSet(),
                            episodesFavSet = episodesList.mapNotNull {
                                if (it.esFavorito) it.id else null
                            }.toSet()
                        )
                }
            }
        }
    }

    fun toggleFavoriteOrView(episode: Episode) {
        viewModelScope.launch {
            val existsEpisode: Episode? = getEpisodeDbByIdUseCase.execute(episode.id) // se comprueba si existe el personaje en la BD

            if (existsEpisode == null) {
                insertEpisodeDbUseCase.execute(episode)
            } else {
                updateEpisodeDbStatusUseCase.execute(episodeId = episode.id, esFavorito = episode.esFavorito, esVisto = episode.esVisto)
            }

            loadFavoritesOrViews() // 🔄 Actualiza la lista de favoritos
        }
    }

}