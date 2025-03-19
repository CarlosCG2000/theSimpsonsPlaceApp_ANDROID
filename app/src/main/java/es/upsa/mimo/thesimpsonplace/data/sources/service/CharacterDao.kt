package es.upsa.mimo.thesimpsonplace.data.sources.service

import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDto

// Toda fuente de datos debe de implementar esta interfaz, con sus correspondientes acciones
interface CharacterDao {
    fun getAllCharacters(): List<CharacterDto>
    fun getFilterNameCharacters(name: String): List<CharacterDto>
}