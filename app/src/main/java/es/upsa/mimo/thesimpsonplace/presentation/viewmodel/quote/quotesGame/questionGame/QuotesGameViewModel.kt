package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesGame.questionGame

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.GetQuestionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesGameViewModel @Inject constructor( val getQuestionsUseCase: GetQuestionsUseCase ): ViewModel() {
    private val _stateQuestions: MutableStateFlow<QuotesGameUI> = MutableStateFlow(QuotesGameUI()) // Asincrono esta en un hilo secundario
    val stateQuestions: StateFlow<QuotesGameUI> = _stateQuestions.asStateFlow()

    fun getQuestions(){
        viewModelScope.launch {
            _stateQuestions.update { it.copy(isLoading = true) }

            try {
                val questions = getQuestionsUseCase()
                _stateQuestions.update { it.copy(questions = questions) }
            } catch (e: Exception) {
                Log.e("QuotesGameViewModel", "Error al obtener preguntas", e)
            } finally {
                _stateQuestions.update { it.copy(isLoading = false) }
            }
        }
    }
}