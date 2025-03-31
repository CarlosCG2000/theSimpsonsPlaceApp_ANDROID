package es.upsa.mimo.thesimpsonplace.domain.usescases.character

import es.upsa.mimo.thesimpsonplace.domain.models.Character

interface GetAllCharactersUseCase {
    suspend fun execute(): List<Character>
}