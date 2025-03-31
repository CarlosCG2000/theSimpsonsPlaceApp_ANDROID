package es.upsa.mimo.thesimpsonplace.domain.usescases.quote

import es.upsa.mimo.thesimpsonplace.domain.models.Quote

interface GetQuoteDbByCitaUseCase {
    suspend operator fun invoke(cita: String): Quote?
}