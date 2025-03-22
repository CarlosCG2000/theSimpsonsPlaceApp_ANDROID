package es.upsa.mimo.thesimpsonplace.domain.repository.impl

import es.upsa.mimo.thesimpsonplace.data.sources.database.QuoteDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.sources.service.QuoteDao
import es.upsa.mimo.thesimpsonplace.domain.repository.QuoteRepository
import es.upsa.mimo.thesimpsonplace.domain.entities.Quote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// 'QuoteRepositoryImpl' usa inyección de dependencias (api y db).
// Separa API y BD, cumpliendo el Principio de Responsabilidad Única (SRP).
class QuoteRepositoryImpl(private val apiDao: QuoteDao, private val dbDao: QuoteDatabaseDao) : QuoteRepository {

    override suspend fun getQuotes(numElementos: Int, textPersonaje: String): List<Quote> {
        return withContext(Dispatchers.IO) {
            apiDao.getQuotes(numElementos, textPersonaje)
        }
    }

    override suspend fun getAllQuoteDb(): List<Quote> {
        return withContext(Dispatchers.IO) {
            dbDao.getAllQuoteDb()
        }
    }

    override suspend  fun insertQuoteDb(quote: Quote) {
        return withContext(Dispatchers.IO) {
            dbDao.insertQuoteDb(quote)
        }
    }

    override suspend fun deleteQuoteDb(cita: String) {
        return withContext(Dispatchers.IO) {
            dbDao.deleteQuoteDb(cita)
        }
    }

}
