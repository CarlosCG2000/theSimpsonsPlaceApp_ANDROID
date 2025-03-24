package es.upsa.mimo.thesimpsonplace.data.sources.service

import es.upsa.mimo.thesimpsonplace.data.entities.quote.QuoteDto
import es.upsa.mimo.thesimpsonplace.domain.entities.Quote
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteDao {
    // https://thesimpsonsquoteapi.glitch.me/quotes?count=15&character=ho
    @GET("/quotes")
    suspend fun getQuotes(@Query("count") numElementos:Int = 10, // permiten pasar parámetros opcionales.
                          @Query("character") textPersonaje:String = ""): List<QuoteDto> // Debe ser suspend para ejecutarse en I/O (Kotlin Coroutines para llamadas asíncronas)
}



