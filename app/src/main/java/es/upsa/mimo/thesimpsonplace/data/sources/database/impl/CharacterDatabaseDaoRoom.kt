package es.upsa.mimo.thesimpsonplace.data.sources.database.impl

import es.upsa.mimo.thesimpsonplace.data.sources.database.CharacterDatabaseDao
import es.upsa.mimo.thesimpsonplace.domain.entities.Character

// Implementación de 'CharacterDBDao' (acciones) en base de datos de 'Room'
class CharacterDatabaseDaoRoom: CharacterDatabaseDao {
    override fun fetchAllCharactersDb(): List<Character> {
        TODO("Not yet implemented")
    }

    override fun fetchCharacterByIdDb(id: Int): Character {
        TODO("Not yet implemented")
    }

    override fun insertCharacterDb(character: Character) {
        TODO("Not yet implemented")
    }

    override fun updateCharacterDb(id: Int) {
        TODO("Not yet implemented")
    }

}