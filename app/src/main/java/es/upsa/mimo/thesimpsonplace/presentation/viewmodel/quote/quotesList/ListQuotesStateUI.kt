package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesList

import es.upsa.mimo.thesimpsonplace.domain.models.Quote

data class ListQuotesStateUI( val quotes: List<Quote> = emptyList(), // val quotes: Result<List<Quote>> = Result.success(emptyList()),
                              val isLoading: Boolean = false)