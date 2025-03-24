package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodeDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetAllEpisodesUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodeByIdUseCase
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.TheSimpsonPlaceApp
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList.ListEpisodesStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList.ListEpisodesViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsEpisodeViewModel @Inject constructor( val getEpisodeByIdUseCase: GetEpisodeByIdUseCase ) : ViewModel() {
    private val _episodeState: MutableStateFlow<DetailsEpisodeStateUI> = MutableStateFlow(DetailsEpisodeStateUI()) // en hilo secundario
    val episodeState: StateFlow<DetailsEpisodeStateUI> = _episodeState.asStateFlow()

    fun getEpisodeById(id:String){
        viewModelScope.launch {
            val getEpisode: Episode? = getEpisodeByIdUseCase.execute(id)

            _episodeState.update {
                it.copy(getEpisode)
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
//                return DetailsEpisodeViewModel (application.getEpisodeById) as T
//            }
//        }
//    }

}