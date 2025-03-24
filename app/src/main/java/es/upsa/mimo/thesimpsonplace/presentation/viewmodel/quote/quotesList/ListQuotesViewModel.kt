package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.GetQuotesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListQuotesViewModel @Inject constructor( val getQuotesUseCase: GetQuotesUseCase ): ViewModel() {
    private val _stateQuotes: MutableStateFlow<ListQuotesStateUI> = MutableStateFlow(ListQuotesStateUI()) // Asincrono esta en un hilo secundario
    val stateQuotes: StateFlow<ListQuotesStateUI> = _stateQuotes.asStateFlow()

    fun getQuotes(numElementos: Int = 5, textPersonaje: String = ""){
        viewModelScope.launch {
            val quotes = getQuotesUseCase.execute(numElementos, textPersonaje)

            _stateQuotes.update {
                it.copy(quotes)
            }
        }
    }

}