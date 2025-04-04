package es.upsa.mimo.thesimpsonplace.data.daos.remote

import es.upsa.mimo.thesimpsonplace.data.entities.quote.QuoteDTO
import retrofit2.http.GET
import retrofit2.http.Query

// Esta interfaz es para llamar a un Backend (servidor) implementada en RetrFit, en este caso llama a una ApiRest.
// A no ser un ApiRest propia y no estar conectada a una BD a dicho servidor, solo voy a poder hacer operaciones GET.
// API: https://thesimpsonsquoteapi.glitch.me/quotes?count=15&character=ho
interface QuoteDao {

    @GET("/quotes")
    suspend fun getQuotes(@Query("count") numElementos:Int = 10, // permiten pasar parámetros opcionales.
                          @Query("character") textPersonaje:String = ""): List<QuoteDTO> // Debe ser suspend para ejecutarse en I/O (Kotlin Coroutines para llamadas asíncronas)
}



