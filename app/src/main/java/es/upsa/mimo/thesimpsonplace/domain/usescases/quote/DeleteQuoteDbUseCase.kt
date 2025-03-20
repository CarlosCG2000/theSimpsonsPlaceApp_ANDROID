package es.upsa.mimo.thesimpsonplace.domain.usescases.quote

import es.upsa.mimo.thesimpsonplace.domain.entities.Quote

interface DeleteQuoteDbUseCase {
    fun execute(cita: String)
}