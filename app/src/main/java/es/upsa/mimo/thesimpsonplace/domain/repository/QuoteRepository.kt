package es.upsa.mimo.thesimpsonplace.domain.repository

import es.upsa.mimo.thesimpsonplace.domain.entities.Quote

interface QuoteRepository {
    suspend fun getQuotes(numElementos: Int = 5, textPersonaje: String = ""): List<Quote>
    suspend fun getAllQuoteDb(): List<Quote>
    suspend fun insertQuoteDb(quote: Quote)
    suspend fun deleteQuoteDb(cita: String)
}




