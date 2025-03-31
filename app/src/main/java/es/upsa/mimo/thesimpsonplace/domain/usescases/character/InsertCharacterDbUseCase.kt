package es.upsa.mimo.thesimpsonplace.domain.usescases.character

import es.upsa.mimo.thesimpsonplace.domain.models.Character

interface InsertCharacterDbUseCase {
    // suspend operator fun invoke(character: Character)
    suspend fun execute(character: Character): Unit
}