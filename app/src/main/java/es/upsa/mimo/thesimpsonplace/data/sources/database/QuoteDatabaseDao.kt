package es.upsa.mimo.thesimpsonplace.data.sources.database

import es.upsa.mimo.thesimpsonplace.domain.entities.Quote

interface  QuoteDatabaseDao {
    fun getAllQuoteDb(): List<Quote>
    fun getQuoteByCitaDb(cita: String): Quote
    fun insertQuoteDb(quote: Quote)
    fun deleteQuoteDb(cita: String)
}