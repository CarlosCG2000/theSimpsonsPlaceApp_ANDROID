package es.upsa.mimo.thesimpsonplace.data.daos.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterEntity
import kotlinx.coroutines.flow.Flow

// Las operaciones para la Base de datos de la entidad de Character
@Dao
interface CharacterDatabaseDao {
    @Query("SELECT * FROM characters ORDER BY nombre ASC") // ORDER BY id ASC
    fun getAllCharactersDb(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM characters WHERE id = :id LIMIT 1")
    suspend fun getCharacterDbById(id: Int): CharacterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterDb(character: CharacterEntity)

    @Delete
    suspend fun deleteCharacterDb(character: CharacterEntity)
}