package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.quote

import es.upsa.mimo.thesimpsonplace.domain.models.Quote
import es.upsa.mimo.thesimpsonplace.domain.repository.QuoteRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.DeleteQuoteDbUseCase
import javax.inject.Inject

class DeleteQuoteDbUseCaseImpl @Inject constructor(val repository: QuoteRepository): DeleteQuoteDbUseCase {
    override suspend operator fun invoke(quote: Quote) =
        repository.deleteQuoteDb(quote)
}
