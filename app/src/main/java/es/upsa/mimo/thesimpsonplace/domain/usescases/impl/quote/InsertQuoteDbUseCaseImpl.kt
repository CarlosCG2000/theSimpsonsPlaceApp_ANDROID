package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.quote

import es.upsa.mimo.thesimpsonplace.domain.entities.Quote
import es.upsa.mimo.thesimpsonplace.domain.repository.QuoteRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.InsertQuoteDbUseCase

class InsertQuoteDbUseCaseImpl(val repository: QuoteRepository): InsertQuoteDbUseCase {
    override suspend fun execute(quote: Quote) = repository.insertQuoteDb(quote)
}