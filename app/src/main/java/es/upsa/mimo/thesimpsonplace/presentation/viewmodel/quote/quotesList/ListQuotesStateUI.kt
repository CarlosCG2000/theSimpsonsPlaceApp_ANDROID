package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesList

import es.upsa.mimo.thesimpsonplace.domain.entities.Quote

data class ListQuotesStateUI( val quotes: List<Quote> = emptyList(),
                              val isLoading: Boolean = false)