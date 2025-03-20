package es.upsa.mimo.thesimpsonplace.data.mappers

import es.upsa.mimo.thesimpsonplace.data.entities.quote.QuoteDto
import es.upsa.mimo.thesimpsonplace.domain.entities.Quote
import java.net.URL

// Función de Extensión para convertir QuoteDto a Quote
fun QuoteDto.toQuote(): Quote {

    return Quote(
        cita = cita,
        personaje = personaje,
        imagen = URL(imagen)
    )
}

/**
    // toQuote() y toQuoteEntity() permiten transformar datos entre BD y lógica de negocio.
    // Mapper: Convertir QuoteEntity a Quote
    fun QuoteEntity.toQuote(): Quote {
        return Quote(
            id = id,
            cita = cita,
            personaje = personaje,
            imagen = URL(imagen)
        )
    }

    // Mapper: Convertir Quote a QuoteEntity
    fun Quote.toQuoteEntity(): QuoteEntity {
        return QuoteEntity(
            id = id,
            cita = cita,
            personaje = personaje,
            imagen = imagen.toString()
        )
    }
*/