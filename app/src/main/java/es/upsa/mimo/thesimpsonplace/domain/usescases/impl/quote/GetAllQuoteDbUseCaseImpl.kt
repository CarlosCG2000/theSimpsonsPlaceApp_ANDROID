package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.quote

import es.upsa.mimo.thesimpsonplace.domain.entities.Quote
import es.upsa.mimo.thesimpsonplace.domain.repository.QuoteRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.GetAllQuoteDbUseCase
import javax.inject.Inject

class GetAllQuoteDbUseCaseImpl  @Inject constructor(val repository: QuoteRepository): GetAllQuoteDbUseCase {
    override suspend fun execute(): List<Quote> = repository.getAllQuoteDb()
}