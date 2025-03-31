package es.upsa.mimo.thesimpsonplace.data.sources.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDb
import kotlinx.coroutines.flow.Flow

//interface CharacterDatabaseDao {
//    suspend fun getAllCharactersDb(): List<Character> // Obtener todos los personajes de la base de datos
//    suspend fun insertCharacterDb(character: Character): Unit // Insertar personaje en la base de datos
//    suspend fun deleteCharacterDb(id: Int): Unit // Borrar personaje en la base de datos
//}

//interface CharacterDatabaseDao {
//    fun getAllCharactersDb(): Flow<List<CharacterDb>>
//    suspend fun insertCharacterDb(character: CharacterDb): Unit
//    suspend fun deleteCharacterDb(character: CharacterDb): Unit
//}

// Las operaciones para la Base de datos de la entidad de Character
@Dao
interface CharacterDatabaseDaoRoom {
    @Query("SELECT * FROM characters ORDER BY nombre ASC") // ORDER BY id ASC
    fun getAllCharactersDb(): Flow<List<CharacterDb>>

    @Query("SELECT * FROM characters WHERE id = :id LIMIT 1")
    suspend fun getCharacterDbById(id: Int): CharacterDb?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterDb(character: CharacterDb)

    @Delete
    suspend fun deleteCharacterDb(character: CharacterDb)
}