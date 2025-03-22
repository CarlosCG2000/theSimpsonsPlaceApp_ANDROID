package es.upsa.mimo.thesimpsonplace.data.sources.database.impl

import es.upsa.mimo.thesimpsonplace.data.sources.database.CharacterDatabaseDao
import es.upsa.mimo.thesimpsonplace.domain.entities.Character

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