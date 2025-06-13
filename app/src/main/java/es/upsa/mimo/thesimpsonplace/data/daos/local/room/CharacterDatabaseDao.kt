package es.upsa.mimo.thesimpsonplace.data.daos.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterEntity
import kotlinx.coroutines.flow.Flow

// Operaciones para la BD de la entidad de Character
@Dao
interface CharacterDatabaseDao {
    // Obtener todos los personajes de la BD
    @Query("""    SELECT *                            
                  FROM ${CharacterEntity.TABLE_NAME} 
                  ORDER BY nombre ASC    
           """ )
    fun getAllCharactersDb(): Flow<List<CharacterEntity>>

    // Obtener un personaje por su ID de la BD
    @Query("""    SELECT *                            
                  FROM ${CharacterEntity.TABLE_NAME}  
                  WHERE id = :id                      
                  LIMIT 1                              
           """)
    suspend fun getCharacterDbById(id: Int): CharacterEntity?

    // Insertar un personaje en la BD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterDb(character: CharacterEntity)

    // Eliminar un personaje de la BD
    @Delete
    suspend fun deleteCharacterDb(character: CharacterEntity)
}