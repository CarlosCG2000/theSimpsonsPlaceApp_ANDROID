package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.models.Episode
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetAllEpisodesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListEpisodesViewModel @Inject constructor(val getAllEpisodesUseCase: GetAllEpisodesUseCase ): ViewModel() {
    private val _episodesState: MutableStateFlow<ListEpisodesStateUI> = MutableStateFlow(ListEpisodesStateUI()) // en hilo secundario
    val episodesState: StateFlow<ListEpisodesStateUI> = _episodesState.asStateFlow()

    fun getAllEpisodes(){
        viewModelScope.launch {
            _episodesState.update { it.copy(isLoading = true) }

            val getAllEpisodes: List<Episode> = getAllEpisodesUseCase()

            _episodesState.update {
                it.copy(episodes = getAllEpisodes, isLoading = false)
            }
        }
    }

//    //  Inyección de dependecias manual
//    companion object {
//        fun factory(): Factory = object : Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
//
//                val application = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TheSimpsonPlaceApp
//
//                return ListEpisodesViewModel (application.getAllEpisodes) as T
//            }
//        }
//    }

}