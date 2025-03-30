package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.quote

import es.upsa.mimo.thesimpsonplace.domain.entities.Quote
import es.upsa.mimo.thesimpsonplace.domain.repository.QuoteRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.GetAllQuoteDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.GetQuoteDbByCitaUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuoteDbByCitaUseCaseImpl  @Inject constructor(val repository: QuoteRepository): GetQuoteDbByCitaUseCase {
    override suspend fun execute(cita: String): Quote? = repository.getQuoteDbByCita(cita)
}