package es.upsa.mimo.thesimpsonplace.domain.usescases.quote

import es.upsa.mimo.thesimpsonplace.domain.models.Quote


interface DeleteQuoteDbUseCase {
    suspend fun execute(quote: Quote)
    // operator fun invoke(quote: Quote) --> OTRA OPCIÓN DE DEFINIRLO
}