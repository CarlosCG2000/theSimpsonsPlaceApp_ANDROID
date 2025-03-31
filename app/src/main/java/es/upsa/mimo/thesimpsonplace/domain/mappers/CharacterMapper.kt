package es.upsa.mimo.thesimpsonplace.domain.mappers

import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDb
import es.upsa.mimo.thesimpsonplace.domain.entities.Character

fun Character.toCharacterDb(): CharacterDb {
    return CharacterDb( id = id,
        nombre = nombre,
        genero = genero,
        imagen = imagen)
}