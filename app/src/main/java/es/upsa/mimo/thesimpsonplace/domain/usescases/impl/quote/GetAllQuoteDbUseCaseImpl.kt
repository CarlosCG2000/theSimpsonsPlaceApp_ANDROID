package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.quote

import es.upsa.mimo.thesimpsonplace.domain.entities.Quote
import es.upsa.mimo.thesimpsonplace.domain.repository.QuoteRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.GetAllQuoteDbUseCase

class GetAllQuoteDbUseCaseImpl(val repository: QuoteRepository): GetAllQuoteDbUseCase {
    override fun execute(): List<Quote> = repository.getAllQuoteDb()
}