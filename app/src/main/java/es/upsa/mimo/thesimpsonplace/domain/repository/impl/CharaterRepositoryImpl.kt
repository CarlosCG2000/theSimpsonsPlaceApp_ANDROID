package es.upsa.mimo.thesimpsonplace.domain.repository.impl

import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDTO
import es.upsa.mimo.thesimpsonplace.data.mappers.toCharacter
import es.upsa.mimo.thesimpsonplace.data.sources.local.CharacterDatabaseDaoRoom
import es.upsa.mimo.thesimpsonplace.data.sources.remote.CharacterDao
import es.upsa.mimo.thesimpsonplace.domain.entities.Character
import es.upsa.mimo.thesimpsonplace.domain.mappers.toCharacterDb
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

// 	@Inject constructor(...): Hilt puede inyectarlo directamente sin necesidad de un @Provides en el AppModule.
class CharaterRepositoryImpl @Inject constructor(val dao: CharacterDao,
                                                 private val databaseDao: CharacterDatabaseDaoRoom): CharaterRepository {

    override suspend fun getAllCharacters(): List<Character> {
        return withContext(Dispatchers.IO) { // withContext cambia el contexto de ejecución de la corrutina sin crear una nueva
            // 🚀 1️⃣ Obtener todos los personajes del JSON/API y mapearlos a la entidad `Character`
            val allCharactersDto = dao.getAllCharacters()
            val allCharacters = allCharactersDto.map { it.toCharacter() }

            allCharacters

            // 🚀 2️⃣ Obtener los personajes favoritos de la BD y convertirlos en un Map para acceso rápido
            // val favoriteCharactersMap = databaseDao.getAllCharactersDb().associateBy { it.id }

            // 🚀 3️⃣ Fusionar datos del JSON con la BD (si el personaje está en la BD, tomar `esFavorito` de ahí)
//            allCharacters.map { character ->
//                val characterDb = favoriteCharactersMap[character.id] // Buscar personaje en la BD
//                character.copy(
//                    esFavorito = characterDb?.esFavorito == true // Si está en la BD, usar su estado real
//                )
//            }

        }
    }

    override suspend fun getCharactersByName(name: String): List<Character> {
        return withContext(Dispatchers.IO) {
            val filteredCharactersDto: List<CharacterDTO> = dao.getCharactersByName(name = name)
            val filteredCharacters: List<Character> = filteredCharactersDto.map { it.toCharacter() }
            filteredCharacters

            // 🚀 2️⃣ Obtener los personajes favoritos de la BD y convertirlos en un Map para acceso rápido
            // val favoriteCharactersMap = databaseDao.getAllCharactersDb().associateBy { it.id }

            // 🚀 3️⃣ Fusionar datos del JSON con la BD (si el personaje está en la BD, tomar `esFavorito` de ahí)
//            filteredCharacters.map { character ->
//                val characterDb = favoriteCharactersMap[character.id] // Buscar personaje en la BD
//                character.copy(
//                    esFavorito = characterDb?.esFavorito == true // Si está en la BD, usar su estado real
//                )
//            }
        }
    }

    override fun getAllCharactersDb(): Flow<List<Character>> {
        return databaseDao.getAllCharactersDb().map { list ->
            list.map { it.toCharacter() }
        }
    }

    override suspend fun getCharacterDbById(id: Int): Character? {
        return databaseDao.getCharacterDbById(id)?.toCharacter()
    }

    override suspend fun insertCharacterDb(character: Character) {
        databaseDao.insertCharacterDb(character.toCharacterDb())
    }

    override suspend fun deleteCharacterDb(character: Character) {
        databaseDao.deleteCharacterDb(character.toCharacterDb())
    }

}