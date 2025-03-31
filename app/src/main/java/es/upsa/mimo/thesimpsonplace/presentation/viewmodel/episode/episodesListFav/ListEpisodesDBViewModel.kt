package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.models.Episode
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetAllEpisodesDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodeDbByIdUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.InsertEpisodeDbUseCase
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
//    private val getWatchedEpisodesUseCase: GetWatchedEpisodesUseCase,     // NO SE NECESITA AQUÍ, PERO SE UTILIZAR EN OTRO CASO DE USO DE FILTROS DE EPISODIOS POR VIEWS
//    private val isEpisodeDbWatchedUseCase: IsEpisodeDbWatchedUseCase,     // NO SE NECESITA
//    private val isEpisodeDbFavoriteUseCase: IsEpisodeDbFavoriteUseCase,   // NO SE NECECITA
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

            _stateEpisodesFavOrView.update { it.copy(isLoading = true) }

            getAllEpisodesDbUseCase().collect { episodesList ->

                _stateEpisodesFavOrView.update {

                    it.copy( episodes = episodesList,
                             episodesView = episodesList.filter { it.esVisto },         // CREO QUE NO LO USO
                             episodesFav = episodesList.filter { it.esFavorito },       // CREO QUE NO LO USO
                             episodesSet = episodesList.map { it.id }.toSet(),   // CREO QUE NO LO USO
                             episodesViewSet = episodesList.mapNotNull {
                               if (it.esVisto) it.id else null
                            }.toSet(),
                            episodesFavSet = episodesList.mapNotNull {
                                if (it.esFavorito) it.id else null
                            }.toSet(),
                            isLoading = false
                        )
                }
            }
        }
    }

    fun toggleFavoriteOrView(episode: Episode, fav: Boolean, view: Boolean) {
        viewModelScope.launch {
            val existsEpisode = getEpisodeDbByIdUseCase(episode.id)// se comprueba si existe el personaje en la BD

            if (existsEpisode == null) {
                insertEpisodeDbUseCase(episode.copy(esFavorito = fav, esVisto = view))
            } else {
                updateEpisodeDbStatusUseCase(episodeId = existsEpisode.id, esFavorito = fav, esVisto = view)
            }

            // 🔄 Actualizar el estado después de modificar la BD
            _stateEpisodesFavOrView.update { currentState ->
                val updatedEpisodes = currentState.episodes.map {
                    if (it.id == episode.id) it.copy(esFavorito = fav, esVisto = view) else it
                }

                currentState.copy(
                    episodes = updatedEpisodes,
                    episodesFavSet = updatedEpisodes.filter { it.esFavorito }.map { it.id }.toSet(),
                    episodesViewSet = updatedEpisodes.filter { it.esVisto }.map { it.id }.toSet()
                )
            }
        }
    }

}