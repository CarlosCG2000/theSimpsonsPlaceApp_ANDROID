package es.upsa.mimo.thesimpsonplace.data.mappers

import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDb
import es.upsa.mimo.thesimpsonplace.data.entities.quote.QuoteDb
import es.upsa.mimo.thesimpsonplace.data.entities.quote.QuoteDto
import es.upsa.mimo.thesimpsonplace.domain.entities.Character
import es.upsa.mimo.thesimpsonplace.domain.entities.Quote
import java.net.URL
import kotlin.Boolean

// Función de Extensión para convertir QuoteDto a Quote
fun QuoteDto.toQuote(/*esFavorito: Boolean = false*/): Quote {
    return Quote(
        cita = cita ?: "Cita desconocida",
        personaje = personaje?: "Personaje desconocida",
        imagen = imagen?.let { URL(it) } ?: URL("https://www.redbubble.com/es/i/tarjeta-de-felicitacion/Usuario-desconocido-de-Perzikman1/51829137.5MT14"),
        esFavorito = false // da igula este valor luego se modificara en el Quote cuando llegue a la View
    )
}

// ROOM
fun Quote.toQuoteDb(): QuoteDb {
    return QuoteDb(
        cita = cita,
        personaje = personaje,
        imagen = imagen)
}


fun QuoteDb.toQuote(): Quote {
    return Quote(
        cita = cita,
        personaje = personaje,
        imagen = imagen,
        esFavorito = true)
}