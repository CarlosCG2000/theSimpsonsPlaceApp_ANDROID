package es.upsa.mimo.thesimpsonplace.data.sources.database

import es.upsa.mimo.thesimpsonplace.domain.entities.Character

interface CharacterDatabaseDao {
    fun fetchAllCharactersDb(): List<Character> // Obtener todos los personajes de la base de datos
    fun fetchCharacterByIdDb(id: Int): Character // Obtener un personaje espefifico de la base de datos
    fun insertCharacterDb(character: Character): Unit // Insertar personaje en la base de datos
    fun updateCharacterDb(id: Int): Unit // Actualizar personaje en la base de datos
}