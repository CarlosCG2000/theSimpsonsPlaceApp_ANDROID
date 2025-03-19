package es.upsa.mimo.thesimpsonplace.domain.usescases

import es.upsa.mimo.thesimpsonplace.domain.entities.Character

interface InsertCharacterDbUseCase {
    fun execute(character: Character): Unit
}