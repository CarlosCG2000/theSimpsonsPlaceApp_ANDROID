package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesGame.resultGame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.repository.impl.GameRepositoryImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.game.GetGameStatsUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.game.ResetStatsUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.game.UpdateStatsUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.GetQuestionsUseCase
import es.upsa.mimo.thesimpsonplace.utils.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultGameViewModel @Inject constructor( val getGameStatsUseCase: GetGameStatsUseCase,
                                               val updateStatsUseCase: UpdateStatsUseCase,
                                               val resetStatsUseCase: ResetStatsUseCase): ViewModel(), Logger {

    private val _gameStats: MutableStateFlow<ResultGameUI> = MutableStateFlow(ResultGameUI()) // Asincrono esta en un hilo secundario
    val gameStats: StateFlow<ResultGameUI> = _gameStats.asStateFlow()

    init {
        getStats()
    }

    fun getStats(){
        viewModelScope.launch {
            getGameStatsUseCase.gameStatsFlow.collect { stats ->
                _gameStats.update {
                    it.copy(result = stats)
                }
            }
            logInfo( "Cargando con existo las preguntas ${gameStats.value.result.first} ${gameStats.value.result.second}" )
        }
    }

    fun updateStats(aciertos: Int, preguntas: Int) {
        viewModelScope.launch {
            updateStatsUseCase(aciertos, preguntas)
            _gameStats.update { it.copy(result = aciertos to preguntas) } // ✅ Actualiza el estado en la UI
            logInfo( "Actualizando con existo las preguntas ${gameStats.value.result.first} ${gameStats.value.result.second}" )
        }
    }

    fun resetStats() {
        viewModelScope.launch {
            resetStatsUseCase()
            _gameStats.update { it.copy(result = 0 to 0) } // ✅ Reinicia la UI
            logInfo( "Reiniciando con existo las preguntas ${gameStats.value.result.first} ${gameStats.value.result.second}" )
        }
    }
}