package es.upsa.mimo.thesimpsonplace.domain.mappers

import es.upsa.mimo.thesimpsonplace.data.entities.quote.QuoteEntity
import es.upsa.mimo.thesimpsonplace.domain.models.Quote

fun Quote.toQuoteDb(): QuoteEntity =
         QuoteEntity(
            cita = cita,
            personaje = personaje,
            imagen = imagen)
