package es.upsa.mimo.thesimpsonplace.data.sources.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.upsa.mimo.thesimpsonplace.data.entities.quote.QuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDatabaseDaoRoom {
    @Query("SELECT * FROM quotes ORDER BY personaje ASC") // ORDER BY id ASC
    fun getAllQuotesDb(): Flow<List<QuoteEntity>>

    @Query("SELECT * FROM quotes WHERE cita = :cita LIMIT 1")
    suspend fun getQuoteDbByCita(cita: String): QuoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuoteDb(quote: QuoteEntity)

    @Delete
    suspend fun deleteQuoteDb(quote: QuoteEntity)
}
