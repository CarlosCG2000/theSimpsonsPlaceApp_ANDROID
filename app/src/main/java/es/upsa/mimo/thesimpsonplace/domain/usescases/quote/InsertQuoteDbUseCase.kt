package es.upsa.mimo.thesimpsonplace.domain.usescases.quote

import es.upsa.mimo.thesimpsonplace.domain.models.Quote

interface InsertQuoteDbUseCase {
    suspend operator fun invoke(quote: Quote)
}