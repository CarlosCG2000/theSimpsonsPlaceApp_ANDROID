package es.upsa.mimo.thesimpsonplace.data.sources.database

import es.upsa.mimo.thesimpsonplace.domain.entities.Character

interface CharacterDatabaseDao {
    fun getAllCharactersDb(): List<Character> // Obtener todos los personajes de la base de datos
    fun insertCharacterDb(character: Character): Unit // Insertar personaje en la base de datos
    fun deleteCharacterDb(id: Int): Unit // Borrar personaje en la base de datos
}