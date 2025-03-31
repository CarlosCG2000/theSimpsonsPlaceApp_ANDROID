package es.upsa.mimo.thesimpsonplace.domain.mappers

import es.upsa.mimo.thesimpsonplace.data.entities.quote.QuoteEntity
import es.upsa.mimo.thesimpsonplace.domain.entities.Quote

fun Quote.toQuoteDb(): QuoteEntity {
    return QuoteEntity(
        cita = cita,
        personaje = personaje,
        imagen = imagen)
}
