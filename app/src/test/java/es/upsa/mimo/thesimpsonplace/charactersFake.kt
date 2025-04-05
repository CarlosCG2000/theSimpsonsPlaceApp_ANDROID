package es.upsa.mimo.thesimpsonplace

import es.upsa.mimo.thesimpsonplace.data.entities.character.Gender
import es.upsa.mimo.thesimpsonplace.domain.models.Character

// Crea una “fake database” en vez de usar una base de datos real como Room o una Api.
val charactersFake = mutableListOf(
    Character(id = 1, nombre = "Homer Simpson", genero = Gender.Male, imagen = "homer", esFavorito = true),
    Character(id = 2, nombre = "Marge Simpson", genero = Gender.Female, imagen = "marge", esFavorito = true),
    Character(id = 3, nombre = "Lisa Simpson", genero = Gender.Female, imagen = "lisa", esFavorito = false)
)