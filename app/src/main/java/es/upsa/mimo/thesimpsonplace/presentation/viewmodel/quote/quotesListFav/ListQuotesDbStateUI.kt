package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesListFav

import es.upsa.mimo.thesimpsonplace.domain.models.Quote

data class ListQuotesDbStateUI(val quotesSet: Set<String> = emptySet(),
                                val quotes: List<Quote> = emptyList<Quote>(),
                               val isLoading: Boolean = false)



