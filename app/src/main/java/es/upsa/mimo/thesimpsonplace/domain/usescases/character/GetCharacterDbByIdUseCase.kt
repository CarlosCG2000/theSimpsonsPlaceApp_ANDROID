package es.upsa.mimo.thesimpsonplace.domain.usescases.character

import es.upsa.mimo.thesimpsonplace.domain.entities.Character

interface GetCharacterDbByIdUseCase {
    // suspend operator fun invoke(id: Int): Character?
    suspend fun execute(id: Int): Character?
}