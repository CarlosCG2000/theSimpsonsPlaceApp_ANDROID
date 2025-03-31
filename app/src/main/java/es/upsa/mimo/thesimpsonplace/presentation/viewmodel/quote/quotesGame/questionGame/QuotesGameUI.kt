package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesGame.questionGame

import es.upsa.mimo.thesimpsonplace.domain.models.Question

data class QuotesGameUI (val questions:List<Question> = emptyList(),
                         val isLoading: Boolean = false)
