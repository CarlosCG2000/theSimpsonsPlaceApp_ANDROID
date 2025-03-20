package es.upsa.mimo.thesimpsonplace.domain.repository.impl

import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDto
import es.upsa.mimo.thesimpsonplace.data.mappers.toCharacter
import es.upsa.mimo.thesimpsonplace.data.sources.database.CharacterDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.sources.service.CharacterDao
import es.upsa.mimo.thesimpsonplace.domain.entities.Character
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository

class CharaterRepositoryImpl(val dao: CharacterDao, val databaseDao: CharacterDatabaseDao): CharaterRepository {

    override fun getAllCharacters(): List<Character> {
        // 🚀 1️⃣ Obtener todos los personajes del JSON/API y mapearlos a la entidad `Character`
        val allCharactersDto = dao.getAllCharacters()
        val allCharacters = allCharactersDto.map { it.toCharacter() }

        // 🚀 2️⃣ Obtener los personajes favoritos de la BD y convertirlos en un Map para acceso rápido
        val favoriteCharactersMap = databaseDao.getAllCharactersDb().associateBy { it.id }

        // 🚀 3️⃣ Fusionar datos del JSON con la BD (si el personaje está en la BD, tomar `esFavorito` de ahí)
        return allCharacters.map { character ->
            val characterDb = favoriteCharactersMap[character.id] // Buscar personaje en la BD
            character.copy(
                esFavorito = characterDb?.esFavorito == true // Si está en la BD, usar su estado real
            )
        }
    }

    override fun getCharactersByName(name: String): List<Character> {
        val filteredCharactersDto: List<CharacterDto> = dao.getCharactersByName(name = name)
        val filteredCharacters: List<Character> = filteredCharactersDto.map { it.toCharacter() }

        // 🚀 2️⃣ Obtener los personajes favoritos de la BD y convertirlos en un Map para acceso rápido
        val favoriteCharactersMap = databaseDao.getAllCharactersDb().associateBy { it.id }

        // 🚀 3️⃣ Fusionar datos del JSON con la BD (si el personaje está en la BD, tomar `esFavorito` de ahí)
        return filteredCharacters.map { character ->
            val characterDb = favoriteCharactersMap[character.id] // Buscar personaje en la BD
            character.copy(
                esFavorito = characterDb?.esFavorito == true // Si está en la BD, usar su estado real
            )
        }
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