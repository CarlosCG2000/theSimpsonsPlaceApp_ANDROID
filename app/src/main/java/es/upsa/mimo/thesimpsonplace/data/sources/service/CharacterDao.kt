package es.upsa.mimo.thesimpsonplace.data.sources.service

import android.content.Context
import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDto

// Toda fuente de datos debe de implementar esta interfaz, con sus correspondientes acciones
interface CharacterDao {
    suspend fun getAllCharacters(): List<CharacterDto> // Debe ser suspend para ejecutarse en I/O
    suspend fun getCharactersByName(name: String): List<CharacterDto>
}