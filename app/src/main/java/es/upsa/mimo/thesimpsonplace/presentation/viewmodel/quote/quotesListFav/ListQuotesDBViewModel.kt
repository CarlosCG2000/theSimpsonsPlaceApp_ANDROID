package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesListFav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.models.Quote
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.DeleteQuoteDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.GetAllQuoteDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.GetQuoteDbByCitaUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.InsertQuoteDbUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListQuotesDBViewModel @Inject constructor(
            private val getAllQuotesDbUseCase: GetAllQuoteDbUseCase,
            private val getQuoteDbByCitaUseCase: GetQuoteDbByCitaUseCase,
            private val insertQuoteDbUseCase: InsertQuoteDbUseCase,
            private val deleteQuoteDbUseCase: DeleteQuoteDbUseCase,
    ) : ViewModel() {

    private val _stateQuotesFav= MutableStateFlow<ListQuotesDbStateUI>(ListQuotesDbStateUI())
    val stateQuotesFav: StateFlow<ListQuotesDbStateUI> = _stateQuotesFav.asStateFlow()

    init {
        _stateQuotesFav.update { it.copy(isLoading = true) }
        loadFavorites()
        _stateQuotesFav.update { it.copy(isLoading = false) }
    }

    private fun loadFavorites() {
        _stateQuotesFav.update { it.copy(isLoading = true) }

        viewModelScope.launch {

            getAllQuotesDbUseCase.execute().collect { quotesList ->

                _stateQuotesFav.update {
                    it.copy(quotesSet = quotesList.mapNotNull { it.cita }.toSet(),
                            quotes = quotesList)
                }

            }
        }
    }

    fun toggleFavorite(quote: Quote) {
        viewModelScope.launch {
            val existsCharacter = getQuoteDbByCitaUseCase.execute(quote.cita) // se comprueba si existe el personaje en la BD

            if (existsCharacter == null) {
                insertQuoteDbUseCase.execute(quote)
            } else {
                deleteQuoteDbUseCase.execute(quote)
            }

            loadFavorites() // 🔄 Actualiza la lista de favoritos
        }
    }

}

