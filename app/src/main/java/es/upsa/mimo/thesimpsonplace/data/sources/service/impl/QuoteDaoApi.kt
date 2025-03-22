package es.upsa.mimo.thesimpsonplace.data.sources.service.impl

import es.upsa.mimo.thesimpsonplace.data.sources.service.QuoteDao
import es.upsa.mimo.thesimpsonplace.domain.entities.Quote

class QuoteDaoApi: QuoteDao {

    override suspend fun getQuotes(numElementos: Int, textPersonaje: String): List<Quote> {
        TODO("Not yet implemented")
        // Para hacer una llamada HTTP en Kotlin (equivalente a URLSession en SwiftUI), usa Retrofit con suspend y @GET.
        // Solución: Implementar API con Retrofit
        // Primero, instala la dependencia en build.gradle.kts:
        /**
            dependencies {
            implementation("com.squareup.retrofit2:retrofit:2.9.0")
            implementation("com.squareup.retrofit2:converter-moshi:2.9.0") // Para deserializar JSON con Moshi
            }
        */

        // Ahora, configura la API en QuoteDaoApi usando Retrofit:
        /**
        interface QuoteDao {
            @GET("quotes")
            suspend fun getQuotes(
                @Query("count") numElementos: Int = 10,
                @Query("character") textPersonaje: String = ""
            ): List<QuoteDto>
        }
        */

        // Implementación:
        /**
            class QuoteDaoApi: QuoteDao {
            private val retrofit = Retrofit.Builder()
            .baseUrl("https://thesimpsonsquoteapi.glitch.me/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

            private val service = retrofit.create(QuoteDao::class.java)

            override suspend fun getQuotes(numElementos: Int, textPersonaje: String): List<Quote> {
            return service.getQuotes(numElementos, textPersonaje).map { it.toQuote() }
            }
            }
         */

//        ✅ Explicación:
//        •	Retrofit.Builder() configura la base URL y el convertidor JSON.
//        •	@GET("quotes") hace la petición GET a https://thesimpsonsquoteapi.glitch.me/quotes.
//        •	@Query("count") y @Query("character") permiten pasar parámetros opcionales.
//        •	suspend permite usar Kotlin Coroutines para llamadas asíncronas.
    }

}