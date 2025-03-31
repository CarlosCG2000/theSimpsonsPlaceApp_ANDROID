package es.upsa.mimo.thesimpsonplace.domain.models

import es.upsa.mimo.thesimpsonplace.data.entities.character.Gender

// Entidad principal para las operaciones de los personajes (tanto de 'CharacterDao' como 'CharacterDatabaseDao')
data class Character(
    val id: Int, // Contiene el id como Int
    val nombre: String,
    val genero: Gender, // Contiene el género como Gender
    val imagen: String? = null, // Se obtiene en otro JSON
    var esFavorito: Boolean = false
)
