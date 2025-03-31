package es.upsa.mimo.thesimpsonplace.domain.repository

import es.upsa.mimo.thesimpsonplace.domain.models.Character
import kotlinx.coroutines.flow.Flow

interface CharaterRepository {
    // Casos de uso de la datos obtenido de la fuente principal
    suspend fun getAllCharacters(): List<Character>
    suspend fun getCharactersByName(name: String): List<Character> // Nueva: filtrar los personajes por nombre

    // Casos de uso de la datos de la base de datos de Room
    fun getAllCharactersDb(): Flow<List<Character>>
    suspend fun getCharacterDbById(id: Int): Character?
    suspend fun insertCharacterDb(character: Character)
    suspend fun deleteCharacterDb(character: Character)
}



