package es.upsa.mimo.thesimpsonplace.domain.repository.impl

import es.upsa.mimo.thesimpsonplace.data.mappers.toQuote
import es.upsa.mimo.thesimpsonplace.data.daos.local.room.QuoteDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.daos.remote.QuoteDao
import es.upsa.mimo.thesimpsonplace.domain.repository.QuoteRepository
import es.upsa.mimo.thesimpsonplace.domain.models.Quote
import es.upsa.mimo.thesimpsonplace.domain.mappers.toQuoteDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// 'QuoteRepositoryImpl' usa inyección de dependencias (api y db).
// Separa API y BD, cumpliendo el Principio de Responsabilidad Única (SRP).
class QuoteRepositoryImpl @Inject constructor(private val apiDao: QuoteDao,
                                               private val databaseDao: QuoteDatabaseDao) : QuoteRepository {

    override suspend fun getQuotes(numElementos: Int, textPersonaje: String): Result<List<Quote>> =
        // return withContext(Dispatchers.IO) { // no es necesario en un repositorio si la función ya es suspend, porque Retrofit maneja el cambio de contexto automáticamente.
        try {
            Result.success(
                apiDao.getQuotes(numElementos, textPersonaje).map { quoteDto ->
                    quoteDto.toQuote() // Convertimos a Quote
                }
            )
        } catch (e: Exception){
            Result.failure(e) // recibe una excepcion
            // emptyList<Quote>()
        }
        // }

    override fun getAllQuotesDb(): Flow<List<Quote>> =
         databaseDao.getAllQuotesDb().map { list -> list.map { it.toQuote() } }

    override suspend fun getQuoteDbByCita(cita: String): Quote? =
         databaseDao.getQuoteDbByCita(cita)?.toQuote()

    override suspend fun insertQuoteDb(quote: Quote) =
        databaseDao.insertQuoteDb(quote.toQuoteDb())

    override suspend fun deleteQuoteDb(quote: Quote) =
        databaseDao.deleteQuoteDb(quote.toQuoteDb())

}
