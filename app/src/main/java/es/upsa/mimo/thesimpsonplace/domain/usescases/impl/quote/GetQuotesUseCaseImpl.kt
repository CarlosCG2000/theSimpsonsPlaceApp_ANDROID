package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.quote

import es.upsa.mimo.thesimpsonplace.domain.models.Quote
import es.upsa.mimo.thesimpsonplace.domain.repository.QuoteRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.GetQuotesUseCase
import javax.inject.Inject

class GetQuotesUseCaseImpl @Inject constructor(val repository: QuoteRepository): GetQuotesUseCase {
    override suspend operator fun invoke(numElementos: Int, textPersonaje: String): Result<List<Quote>>
        = repository.getQuotes(numElementos, textPersonaje)
}