package es.upsa.mimo.thesimpsonplace.domain.usescases.quote

import es.upsa.mimo.thesimpsonplace.domain.models.Quote

interface GetQuoteDbByCitaUseCase {
    suspend fun execute(cita: String): Quote?
}