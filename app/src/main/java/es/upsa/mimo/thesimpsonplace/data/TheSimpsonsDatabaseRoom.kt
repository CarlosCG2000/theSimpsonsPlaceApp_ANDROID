package es.upsa.mimo.thesimpsonplace.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDb
import kotlinx.coroutines.flow.Flow


// DEFINIMOS NUESTRA BASE DE DATOS
@Database(entities = [CharacterDb::class],  // Solo tenemos una Entity de momemento
          version = 1,  // Versión 1
          exportSchema = false) // Ponerlo a false porque sino al compilar va a dar error.
abstract class TheSimpsonsDatabaseRoom : RoomDatabase() { // La BD que extiende de RoomDatabase()
    abstract fun characterDbDao(): CharacterDatabaseRoomDao // Las operaciones para la Base de datos de la entidad de Todo
}

// Las operaciones para la Base de datos de la entidad de Character
@Dao
interface CharacterDatabaseRoomDao {

    @Query("SELECT * FROM characters ORDER BY id ASC")
    fun getAllCharactersDb(): Flow<List<CharacterDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterDb(character: CharacterDb)

    @Delete
    suspend fun deleteCharacterDb(character: CharacterDb)
}