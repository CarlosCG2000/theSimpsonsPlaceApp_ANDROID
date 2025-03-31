package es.upsa.mimo.thesimpsonplace.data.sources.remote

import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDTO

// Toda fuente de datos debe de implementar esta interfaz, con sus correspondientes acciones
interface CharacterDao {
    suspend fun getAllCharacters(): List<CharacterDTO> // Debe ser suspend para ejecutarse en I/O
    suspend fun getCharactersByName(name: String): List<CharacterDTO>
}