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

// 	@Inject constructor(...): Hilt puede inyectarlo directamente sin necesidad de un '@Provides' en el AppModule.
class CharaterRepositoryImpl @Inject constructor(private val dao: CharacterDao,
                                                 private val databaseDao: CharacterDatabaseDao): CharaterRepository {

    override suspend fun getAllCharacters(): List<Character> =
                    // 'withContext' cambia el contexto de ejecución de la corrutina sin crear una nueva
                    withContext(Dispatchers.IO) {
                        dao.getAllCharacters().map { it.toCharacter() }
                    }

    override suspend fun getCharactersByName(name: String): List<Character> =
                    withContext(Dispatchers.IO) {
                        dao.getCharactersByName(name = name).map { it.toCharacter() }
                    }

/**
 *  Las llamadas a la base de datos ya se están ejecutando en el hilo de E/S de forma implícita gracias a Room y las funciones suspend. No necesitas añadir withContext(Dispatchers.IO) a esas funciones del     repositorio a menos que tuvieras otra lógica pesada que no fuera de base de datos directamente en el repositorio
 */
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