package es.upsa.mimo.thesimpsonplace.domain.usescases.quote

import es.upsa.mimo.thesimpsonplace.domain.entities.Quote

interface GetQuoteDbByCitaUseCase {
    suspend fun execute(cita: String): Quote?
}