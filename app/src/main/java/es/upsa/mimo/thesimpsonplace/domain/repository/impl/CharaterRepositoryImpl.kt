package es.upsa.mimo.thesimpsonplace.domain.repository.impl

import es.upsa.mimo.thesimpsonplace.data.mappers.toCharacter
import es.upsa.mimo.thesimpsonplace.data.daos.local.room.CharacterDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.daos.remote.CharacterDao
import es.upsa.mimo.thesimpsonplace.domain.models.Character
import es.upsa.mimo.thesimpsonplace.domain.mappers.toCharacterDb
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

// 	@Inject constructor(...): Hilt puede inyectarlo directamente sin necesidad de un @Provides en el AppModule.
class CharaterRepositoryImpl @Inject constructor(val dao: CharacterDao,
                                                 private val databaseDao: CharacterDatabaseDao): CharaterRepository {

    override suspend fun getAllCharacters(): List<Character> =
        withContext(Dispatchers.IO) { // 'withContext' cambia el contexto de ejecución de la corrutina sin crear una nueva
            // 1️⃣ Obtenemos los personajes (List<CharacterDTO>) del JSON/API  y los casteamos al modelo List<Character> para la lógica de la aplicación
            dao.getAllCharacters().map { it.toCharacter() }
            /**
            2️⃣ Obtener los personajes favoritos de la BD y convertirlos en un Map para acceso rápido
             val favoriteCharactersMap = databaseDao.getAllCharactersDb().associateBy { it.id }

            3️⃣ Fusionar datos del JSON con la BD (si el personaje está en la BD, tomar `esFavorito` de ahí)
            allCharacters.map { character ->
                val characterDb = favoriteCharactersMap(character.id]) // Buscar personaje en la BD
                character.copy(
                    esFavorito = characterDb?.esFavorito == true // Si está en la BD, usar su estado real
                )
            }
            */
        }

    override suspend fun getCharactersByName(name: String): List<Character> =
        withContext(Dispatchers.IO) {
            dao.getCharactersByName(name = name).map { it.toCharacter() }
        }

    override fun getAllCharactersDb(): Flow<List<Character>> =
        databaseDao.getAllCharactersDb().map { list ->
            list.map { it.toCharacter() }
        }

    override suspend fun getCharacterDbById(id: Int): Character? =
         databaseDao.getCharacterDbById(id)?.toCharacter()

    override suspend fun insertCharacterDb(character: Character) =
        databaseDao.insertCharacterDb(character.toCharacterDb())

    override suspend fun deleteCharacterDb(character: Character) =
        databaseDao.deleteCharacterDb(character.toCharacterDb())
}