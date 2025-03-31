package es.upsa.mimo.thesimpsonplace.domain.mappers

import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterEntity
import es.upsa.mimo.thesimpsonplace.domain.models.Character

fun Character.toCharacterDb(): CharacterEntity {
    return CharacterEntity( id = id,
        nombre = nombre,
        genero = genero,
        imagen = imagen)
}