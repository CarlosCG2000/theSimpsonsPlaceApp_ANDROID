package es.upsa.mimo.thesimpsonplace.domain.repository

import es.upsa.mimo.thesimpsonplace.domain.models.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    suspend fun getQuotes(numElementos: Int = 5, textPersonaje: String = ""): Result<List<Quote>> // 'Result', forma robusta para manejar errores al llamar al backend

    // Casos de uso de la datos de la base de datos
    fun getAllQuotesDb(): Flow<List<Quote>>
    suspend fun getQuoteDbByCita(cita: String): Quote?
    suspend fun insertQuoteDb(quote: Quote)
    suspend fun deleteQuoteDb(quote: Quote)
}




