package es.upsa.mimo.thesimpsonplace.domain.mappers

import es.upsa.mimo.thesimpsonplace.data.entities.quote.QuoteDb
import es.upsa.mimo.thesimpsonplace.domain.entities.Quote

fun Quote.toQuoteDb(): QuoteDb {
    return QuoteDb(
        cita = cita,
        personaje = personaje,
        imagen = imagen)
}
