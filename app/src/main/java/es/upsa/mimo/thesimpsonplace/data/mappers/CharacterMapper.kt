package es.upsa.mimo.thesimpsonplace.data.mappers

import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDto
import es.upsa.mimo.thesimpsonplace.data.entities.character.Gender
import es.upsa.mimo.thesimpsonplace.domain.entities.Character

// Función de extensión de 'CharacterDto' para mapear a 'Character'
fun CharacterDto.toCharacter()
    = Character (
        id = getIdAsInt() ?: 0, // Convierte id a Int o usa 0 por defecto
        nombre = nombre ?: "Unknown", // Si el nombre es null, usa "Unknown"
        genero = Gender.fromAbbreviation(genero) // Convierte "m" -> Male, "f" -> Female
//      esFavorito = false    // por defecto es 'false'
    )
