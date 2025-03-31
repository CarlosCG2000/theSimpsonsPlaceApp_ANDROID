package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.quote

import es.upsa.mimo.thesimpsonplace.domain.models.Quote
import es.upsa.mimo.thesimpsonplace.domain.repository.QuoteRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.InsertQuoteDbUseCase
import javax.inject.Inject

class InsertQuoteDbUseCaseImpl @Inject constructor(val repository: QuoteRepository): InsertQuoteDbUseCase {
    override suspend operator fun invoke(quote: Quote) =
        repository.insertQuoteDb(quote)
}