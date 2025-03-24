package es.upsa.mimo.thesimpsonplace.data.sources.database

import es.upsa.mimo.thesimpsonplace.domain.entities.Quote

interface QuoteDatabaseDao {
    suspend fun getAllQuoteDb(): List<Quote>
    suspend fun getQuoteByCitaDb(cita: String): Quote
    suspend fun insertQuoteDb(quote: Quote)
    suspend fun deleteQuoteDb(cita: String)
}