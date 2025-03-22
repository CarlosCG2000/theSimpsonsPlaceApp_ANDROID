package es.upsa.mimo.thesimpsonplace.domain.usescases.quote

import es.upsa.mimo.thesimpsonplace.domain.entities.Quote

interface GetAllQuoteDbUseCase {
    suspend fun execute(): List<Quote>
}