package es.upsa.mimo.thesimpsonplace.domain.repository

import es.upsa.mimo.thesimpsonplace.domain.entities.Quote

interface QuoteRepository {
    /*suspend*/ fun getQuotes(numElementos: Int = 5, textPersonaje: String = ""): List<Quote>
    fun getAllQuoteDb(): List<Quote>
    fun insertQuoteDb(quote: Quote)
    fun deleteQuoteDb(cita: String)
}




