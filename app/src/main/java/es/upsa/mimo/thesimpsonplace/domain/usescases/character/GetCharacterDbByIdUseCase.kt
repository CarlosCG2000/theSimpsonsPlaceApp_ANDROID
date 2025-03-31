package es.upsa.mimo.thesimpsonplace.domain.usescases.character

import es.upsa.mimo.thesimpsonplace.domain.models.Character

interface GetCharacterDbByIdUseCase {
    suspend operator fun invoke(id: Int): Character?
    // suspend fun execute(id: Int): Character?
}