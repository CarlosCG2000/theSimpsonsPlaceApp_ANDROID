package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.quote

import es.upsa.mimo.thesimpsonplace.domain.repository.QuoteRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.DeleteQuoteDbUseCase

class DeleteQuoteDbUseCaseImpl(val repository: QuoteRepository): DeleteQuoteDbUseCase {
    override fun execute(cita: String) = repository.deleteQuoteDb(cita)
}
