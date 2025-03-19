package es.upsa.mimo.thesimpsonplace.domain.repository.impl

import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDto
import es.upsa.mimo.thesimpsonplace.data.mappers.toCharacter
import es.upsa.mimo.thesimpsonplace.data.sources.database.CharacterDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.sources.service.CharacterDao
import es.upsa.mimo.thesimpsonplace.domain.entities.Character
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository

class CharaterRepositoryImpl(val dao: CharacterDao, val databaseDao: CharacterDatabaseDao): CharaterRepository {

    override fun getAllCharacters(): List<Character> {
        // 🚀 1️⃣ Cargar datos del JSON/API
        val allCharactersDto: List<CharacterDto> = dao.getAllCharacters() // según tenga definido la extracción en el dao 'CharacterDao'
        val allCharacters: List<Character> = allCharactersDto.map { it.toCharacter() } // realizamos el mapea a la entidad 'Character'
        //return allCharacters

        // 🚀 2️⃣ Cargar datos de la BD
        val allCharactersDB: List<Character> = databaseDao.getAllCharactersDb()

        // 🚀 3️⃣️ Resultado final de los datos
        return allCharacters.map { character ->
            val characterDb = allCharactersDB[character.id]

            if (characterDb != null) {
                character.copy(
                    esFavorito = true
                )
            } else {
                character.copy()
            }
        }
    }

    override fun getCharactersByName(name: String): List<Character> {
        val filterCharacterDto: List<CharacterDto> = dao.getCharactersByName(name = name)
        val filterCharacter: List<Character> = filterCharacterDto.map { it.toCharacter() }
        return filterCharacter
    }

    override fun getAllCharactersDb(): List<Character> {
        return databaseDao.getAllCharactersDb()
    }

    override fun insertCharacterDb(character: Character) {
        return databaseDao.insertCharacterDb(character = character)
    }

    override fun deleteCharacterDb(id: Int) {
        return databaseDao.deleteCharacterDb(id = id)
    }
}