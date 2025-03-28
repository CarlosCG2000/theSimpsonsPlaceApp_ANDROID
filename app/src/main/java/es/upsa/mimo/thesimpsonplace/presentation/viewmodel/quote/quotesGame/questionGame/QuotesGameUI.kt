package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesGame.questionGame

import es.upsa.mimo.thesimpsonplace.domain.entities.Question

data class QuotesGameUI (val questions:List<Question> = emptyList(),
                         val isLoading: Boolean = false)
