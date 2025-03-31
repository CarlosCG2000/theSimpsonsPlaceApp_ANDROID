package es.upsa.mimo.thesimpsonplace.data.sources.local.impl

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.upsa.mimo.thesimpsonplace.data.entities.quote.QuoteDb
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDatabaseDaoRoom {
    @Query("SELECT * FROM quotes ORDER BY personaje ASC") // ORDER BY id ASC
    fun getAllQuotesDb(): Flow<List<QuoteDb>>

    @Query("SELECT * FROM quotes WHERE cita = :cita LIMIT 1")
    suspend fun getQuoteDbByCita(cita: String): QuoteDb?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuoteDb(quote: QuoteDb)

    @Delete
    suspend fun deleteQuoteDb(quote: QuoteDb)
}
