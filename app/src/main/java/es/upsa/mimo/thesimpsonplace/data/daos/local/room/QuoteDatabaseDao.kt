package es.upsa.mimo.thesimpsonplace.data.daos.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.upsa.mimo.thesimpsonplace.data.entities.quote.QuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDatabaseDao {
    // Obtener todos las citas de la BD
    @Query("""  SELECT * 
                FROM ${QuoteEntity.TABLE_NAME} 
                ORDER BY personaje ASC
            """)
    fun getAllQuotesDb(): Flow<List<QuoteEntity>>

    // Obtener una cita por su ID (cta en si) de la BD
    @Query("""  SELECT * 
                FROM ${QuoteEntity.TABLE_NAME}  
                WHERE cita = :cita 
                LIMIT 1
           """)
    suspend fun getQuoteDbByCita(cita: String): QuoteEntity?

    // Insertar una cita en la BD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuoteDb(quote: QuoteEntity)

    // Eliminar una cita de la BD
    @Delete
    suspend fun deleteQuoteDb(quote: QuoteEntity)
}
