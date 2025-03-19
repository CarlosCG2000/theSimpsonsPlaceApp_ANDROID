package es.upsa.mimo.thesimpsonplace.domain.repository

import es.upsa.mimo.thesimpsonplace.domain.entities.Character

interface CharaterRepository {
    // Casos de uso de la datos obtenido de la fuente principal
    fun getAllCharacters(): List<Character>
    fun getCharactersByName(name: String): List<Character> // Nueva: filtrar los personajes por nombre

    // Casos de uso de la datos de la base de datos
    fun getAllCharactersDb(): List<Character> // Obtener todos los personajes de la base de datos
    fun insertCharacterDb(character: Character): Unit // Insertar personaje en la base de datos
    fun deleteCharacterDb(id: Int): Unit // Eliminar personaje en la base de datos
}


