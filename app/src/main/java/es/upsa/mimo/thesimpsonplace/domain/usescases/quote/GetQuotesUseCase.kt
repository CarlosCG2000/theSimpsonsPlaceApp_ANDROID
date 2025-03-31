package es.upsa.mimo.thesimpsonplace.domain.usescases.quote

import es.upsa.mimo.thesimpsonplace.domain.models.Quote

interface GetQuotesUseCase {
    suspend operator fun invoke(numElementos: Int, textPersonaje: String): List<Quote>
}