package es.upsa.mimo.thesimpsonplace.data.sources.database.impl

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
// import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDb
import es.upsa.mimo.thesimpsonplace.data.sources.database.CharacterDatabaseDao
import es.upsa.mimo.thesimpsonplace.domain.entities.Character
import kotlinx.coroutines.flow.Flow

// Implementación de 'CharacterDBDao' (acciones) en base de datos de 'Room'
class CharacterDatabaseDaoRoom: CharacterDatabaseDao {
    override suspend fun getAllCharactersDb(): List<Character> {
        return emptyList()
    }

    override suspend fun insertCharacterDb(character: Character) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCharacterDb(id: Int) {
        TODO("Not yet implemented")
    }
}

//@Dao
//interface CharacterDatabaseDaoRoom {
//
//    @Query("SELECT * FROM characters ORDER BY id ASC")
//    fun getAllCharactersDb(): Flow<List<Character>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertCharacterDb(character: CharacterDb)
//
//    @Delete
//    suspend fun deleteCharacterDb(character: CharacterDb)
//}