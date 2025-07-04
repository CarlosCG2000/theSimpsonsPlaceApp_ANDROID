package es.upsa.mimo.thesimpsonplace.data.mappers

import es.upsa.mimo.thesimpsonplace.data.entities.quote.QuoteEntity
import es.upsa.mimo.thesimpsonplace.data.entities.quote.QuoteDTO
import es.upsa.mimo.thesimpsonplace.domain.models.Quote
import java.net.URL

// Función de Extensión para convertir 'QuoteDto' a 'Quote'
fun QuoteDTO.toQuote(/*esFavorito: Boolean = false*/): Quote {
    return Quote( cita = cita ?: "Cita desconocida",
                  personaje = personaje?: "Personaje desconocida",
                  imagen = imagen?.let { URL(it) } ?: URL("https://www.redbubble.com/es/i/tarjeta-de-felicitacion/Usuario-desconocido-de-Perzikman1/51829137.5MT14"),
                  esFavorito = false // da igual este valor luego se modificará en el Quote cuando llegue a la View
            )
}

fun QuoteEntity.toQuote(): Quote =
     Quote( cita = cita,
            personaje = personaje,
            imagen = imagen,
            esFavorito = true
     )


