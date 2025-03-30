package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.quote

import es.upsa.mimo.thesimpsonplace.domain.entities.Quote
import es.upsa.mimo.thesimpsonplace.domain.repository.QuoteRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.DeleteQuoteDbUseCase
import javax.inject.Inject

class DeleteQuoteDbUseCaseImpl  @Inject constructor(val repository: QuoteRepository): DeleteQuoteDbUseCase {
    override suspend fun execute(quote: Quote) = repository.deleteQuoteDb(quote)
    // override fun invoke(quote: Quote) = repository.deleteQuoteDb(quote) --> OTRA OPCIÓN DE DEFINIRLO
}
