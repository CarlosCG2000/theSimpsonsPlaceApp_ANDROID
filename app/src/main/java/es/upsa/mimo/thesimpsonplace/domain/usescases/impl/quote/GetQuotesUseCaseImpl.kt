package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.quote

import es.upsa.mimo.thesimpsonplace.domain.entities.Quote
import es.upsa.mimo.thesimpsonplace.domain.repository.QuoteRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.GetQuotesUseCase

class GetQuotesUseCaseImpl(val repository: QuoteRepository): GetQuotesUseCase{
    override fun execute(numElementos: Int, textPersonaje: String): List<Quote>
        = repository.getQuotes(numElementos, textPersonaje)
}