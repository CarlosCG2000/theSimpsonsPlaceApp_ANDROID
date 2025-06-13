package es.upsa.mimo.thesimpsonplace.data.mappers

import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterEntity
import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDTO
import es.upsa.mimo.thesimpsonplace.data.entities.character.Gender
import es.upsa.mimo.thesimpsonplace.domain.models.Character

// Función de extensión de 'CharacterDto' para mapear a 'Character'
fun CharacterDTO.toCharacter(): Character =
    Character ( id = getIdAsInt() ?: 0,
                nombre = nombre ?: "Unknown",             // Si el nombre es null, usa "Unknown"
                genero = Gender.fromAbbreviation(genero), // Convierte "m" -> Male, "f" -> Female
                imagen = imagen ?: "not_specified" )
                // esFavorito = false                     // Por defecto es 'false'

// Función de extensión de 'CharacterEntity' para mapear a 'Character'
fun CharacterEntity.toCharacter(): Character =
    Character( id = id,
               nombre = nombre,
               genero = genero,
               imagen = imagen,
               esFavorito = true )
