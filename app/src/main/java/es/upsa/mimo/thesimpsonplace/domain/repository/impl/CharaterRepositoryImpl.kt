package es.upsa.mimo.thesimpsonplace.domain.repository.impl

import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDto
import es.upsa.mimo.thesimpsonplace.data.mappers.toCharacter
import es.upsa.mimo.thesimpsonplace.data.sources.database.CharacterDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.sources.service.CharacterDao
import es.upsa.mimo.thesimpsonplace.domain.entities.Character
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository

class CharaterRepositoryImpl(val dao: CharacterDao, val databaseDao: CharacterDatabaseDao): CharaterRepository {

    override fun getAllCharacters(): List<Character> {
        val allCharactersDto: List<CharacterDto> = dao.getAllCharacters() // según tenga definido la extracción en el dao 'CharacterDao'
        val allCharacters: List<Character> = allCharactersDto.map { it.toCharacter() } // realizamos el mapea a la entidad 'Character'
        return allCharacters
    }

    override fun getFilterNameCharacters(name: String): List<Character> {
        val filterCharacterDto: List<CharacterDto> = dao.getFilterNameCharacters(name = name)
        val filterCharacter: List<Character> = filterCharacterDto.map { it.toCharacter() }
        return filterCharacter
    }

    override fun fetchAllCharactersDb(): List<Character> {
        return databaseDao.fetchAllCharactersDb()
    }

    override fun fetchCharacterByIdDb(id: Int): Character {
        return databaseDao.fetchCharacterByIdDb(id = id)
    }

    override fun insertCharacterDb(character: Character) {
        return databaseDao.insertCharacterDb(character = character)
    }

    override fun updateCharacterDb(id: Int) {
        return databaseDao.updateCharacterDb(id = id)
    }
}