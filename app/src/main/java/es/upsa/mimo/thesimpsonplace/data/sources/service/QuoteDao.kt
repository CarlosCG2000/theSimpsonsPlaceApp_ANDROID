package es.upsa.mimo.thesimpsonplace.data.sources.service

import es.upsa.mimo.thesimpsonplace.domain.entities.Quote

interface QuoteDao {
    fun getQuotes(numElementos: Int = 10, textPersonaje: String = ""): List<Quote>
}
