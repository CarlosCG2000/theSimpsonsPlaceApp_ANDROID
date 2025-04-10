package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.GetQuotesUseCase
import es.upsa.mimo.thesimpsonplace.utils.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListQuotesViewModel @Inject constructor( val getQuotesUseCase: GetQuotesUseCase ): ViewModel(), Logger {
    private val _stateQuotes: MutableStateFlow<ListQuotesStateUI> = MutableStateFlow(ListQuotesStateUI()) // Asincrono esta en un hilo secundario
    val stateQuotes: StateFlow<ListQuotesStateUI> = _stateQuotes.asStateFlow()

    fun getQuotes(numElementos: Int = 5, textPersonaje: String = ""){
        viewModelScope.launch {
            _stateQuotes.update { it.copy(isLoading = true) }

            try {
                val quotes = getQuotesUseCase(numElementos, textPersonaje) // Se recibe un Result<List<Quote>>, es decir quotes = Result<List<Quote>>
                // getOrNull() obtiene la lista solo si el resultado fue un éxito, orEmpty() asegura que, si el resultado es null, devuelva una lista vacía en lugar de fallar.
                _stateQuotes.update { it.copy(quotes = quotes.getOrNull().orEmpty()) }
            } catch (e: Exception) {
                logError( "Error al obtener frases: $e")
            } finally {
                _stateQuotes.update { it.copy(isLoading = false) }
            }
            logInfo( "Cargando con éxito las frases ${stateQuotes.value.quotes.size} ${stateQuotes.value.quotes.firstOrNull()?.cita}")
        }
    }
}