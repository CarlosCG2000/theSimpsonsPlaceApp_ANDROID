package es.upsa.mimo.thesimpsonplace.domain.usescases.character

import es.upsa.mimo.thesimpsonplace.domain.entities.Character

interface InsertCharacterDbUseCase {
    suspend fun execute(character: Character): Unit
}