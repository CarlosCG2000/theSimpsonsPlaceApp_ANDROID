package es.upsa.mimo.thesimpsonplace.data.sources.service

import es.upsa.mimo.thesimpsonplace.domain.entities.Quote

interface QuoteDao {
    suspend fun getQuotes(numElementos: Int = 10, textPersonaje: String = ""): List<Quote> // Debe ser suspend para ejecutarse en I/O
}
