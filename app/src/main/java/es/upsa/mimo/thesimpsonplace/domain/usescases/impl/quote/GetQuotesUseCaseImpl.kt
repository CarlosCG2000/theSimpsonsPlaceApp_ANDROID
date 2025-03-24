package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.quote

import es.upsa.mimo.thesimpsonplace.domain.entities.Quote
import es.upsa.mimo.thesimpsonplace.domain.repository.QuoteRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.GetQuotesUseCase
import javax.inject.Inject

class GetQuotesUseCaseImpl  @Inject constructor(val repository: QuoteRepository): GetQuotesUseCase{
    override suspend fun execute(numElementos: Int, textPersonaje: String): List<Quote>
        = repository.getQuotes(numElementos, textPersonaje)
}