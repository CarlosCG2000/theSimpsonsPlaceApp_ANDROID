package es.upsa.mimo.thesimpsonplace.domain.repository.impl

import es.upsa.mimo.thesimpsonplace.data.mappers.toQuote
import es.upsa.mimo.thesimpsonplace.data.sources.database.QuoteDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.sources.service.QuoteDao
import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.QuoteRepository
import es.upsa.mimo.thesimpsonplace.domain.entities.Quote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

// 'QuoteRepositoryImpl' usa inyección de dependencias (api y db).
// Separa API y BD, cumpliendo el Principio de Responsabilidad Única (SRP).
class QuoteRepositoryImpl  @Inject constructor(private val apiDao: QuoteDao,
                                               private val databaseDao: QuoteDatabaseDao) : QuoteRepository {

    override suspend fun getQuotes(numElementos: Int, textPersonaje: String): List<Quote> {

        val quotesDb: List<Quote> = databaseDao.getAllQuoteDb() // si hay episodios en la base de datos con dichos ids se recogen
        return apiDao.getQuotes(numElementos, textPersonaje).map {quoteDto ->
            // Verificamos si la cita ya existe en la base de datos
            val isFavorite = quotesDb.any { it.cita == quoteDto.cita }
            // Convertimos a Quote, estableciendo si es favorito o no
            quoteDto.toQuote(isFavorite)
        }
   // withContext(Dispatchers.IO) { // withContext(Dispatchers.IO) no es necesario en un repositorio si la función ya es suspend, porque Retrofit maneja el cambio de contexto automáticamente.

    // }

    }

    override suspend fun getAllQuoteDb(): List<Quote> {
        return withContext(Dispatchers.IO) {
            databaseDao.getAllQuoteDb()
        }
    }

    override suspend  fun insertQuoteDb(quote: Quote) {
        return withContext(Dispatchers.IO) {
            databaseDao.insertQuoteDb(quote)
        }
    }

    override suspend fun deleteQuoteDb(cita: String) {
        return withContext(Dispatchers.IO) {
            databaseDao.deleteQuoteDb(cita)
        }
    }

}
