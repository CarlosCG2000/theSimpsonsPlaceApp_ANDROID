package es.upsa.mimo.thesimpsonplace.domain.repository.impl

import es.upsa.mimo.thesimpsonplace.data.sources.database.QuoteDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.sources.service.QuoteDao
import es.upsa.mimo.thesimpsonplace.domain.repository.QuoteRepository
import es.upsa.mimo.thesimpsonplace.domain.entities.Quote

// 'QuoteRepositoryImpl' usa inyección de dependencias (api y db).
// Separa API y BD, cumpliendo el Principio de Responsabilidad Única (SRP).
class QuoteRepositoryImpl(private val apiDao: QuoteDao, private val dbDao: QuoteDatabaseDao) : QuoteRepository {

    override /*suspend*/ fun getQuotes(numElementos: Int, textPersonaje: String): List<Quote> {
        return apiDao.getQuotes(numElementos, textPersonaje)
    }

    override fun getAllQuoteDb(): List<Quote> {
        return dbDao.getAllQuoteDb()
    }

    override fun insertQuoteDb(quote: Quote) {
        dbDao.insertQuoteDb(quote)
    }

    override fun deleteQuoteDb(cita: String) {
        dbDao.deleteQuoteDb(cita)
    }

}
