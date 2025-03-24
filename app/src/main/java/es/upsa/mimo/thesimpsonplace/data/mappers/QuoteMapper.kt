package es.upsa.mimo.thesimpsonplace.data.mappers

import es.upsa.mimo.thesimpsonplace.data.entities.quote.QuoteDto
import es.upsa.mimo.thesimpsonplace.domain.entities.Quote
import java.net.URL
import kotlin.Boolean

// Función de Extensión para convertir QuoteDto a Quote
fun QuoteDto.toQuote(esFavorito: Boolean = false): Quote {
    return Quote(
        cita = cita ?: "Cita desconocida",
        personaje = personaje?: "Personaje desconocida",
        imagen = imagen?.let { URL(it) } ?: URL("https://www.redbubble.com/es/i/tarjeta-de-felicitacion/Usuario-desconocido-de-Perzikman1/51829137.5MT14"),
        esFavorito = esFavorito
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