package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetAllEpisodesDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetAllEpisodesUseCase
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.TheSimpsonPlaceApp
import kotlinx.coroutines.delay
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

            val getAllEpisodes: List<Episode> = getAllEpisodesUseCase.execute()

            // delay(2000)

            _episodesState.update {
                it.copy(getAllEpisodes, isLoading = false)
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