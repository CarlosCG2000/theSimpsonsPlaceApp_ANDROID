package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character

import es.upsa.mimo.thesimpsonplace.domain.entities.Character
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.FetchAllCharactersDbUseCase

class FetchAllCharactersDbUseCaseImpl(val repository: CharaterRepository): FetchAllCharactersDbUseCase {
    override suspend fun execute(): List<Character> {
        TODO("Not yet implemented")
    }
}