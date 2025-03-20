package es.upsa.mimo.thesimpsonplace.domain.usescases.quote

import es.upsa.mimo.thesimpsonplace.domain.entities.Quote
import es.upsa.mimo.thesimpsonplace.domain.repository.QuoteRepository

interface InsertQuoteDbUseCase {
    fun execute(quote: Quote)
}