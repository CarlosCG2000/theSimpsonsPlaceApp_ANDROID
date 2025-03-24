package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.quote

import es.upsa.mimo.thesimpsonplace.domain.repository.QuoteRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.DeleteQuoteDbUseCase
import javax.inject.Inject

class DeleteQuoteDbUseCaseImpl  @Inject constructor(val repository: QuoteRepository): DeleteQuoteDbUseCase {
    override suspend fun execute(cita: String) = repository.deleteQuoteDb(cita)
    // override fun invoke(cita: String) = repository.deleteQuoteDb(cita) --> OTRA OPCIÓN DE DEFINIRLO
}
