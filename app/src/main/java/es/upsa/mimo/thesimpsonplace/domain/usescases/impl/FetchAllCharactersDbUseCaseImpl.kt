package es.upsa.mimo.thesimpsonplace.domain.usescases.impl

import es.upsa.mimo.thesimpsonplace.domain.entities.Character
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.FetchAllCharactersDbUseCase

class FetchAllCharactersDbUseCaseImpl(val repository: CharaterRepository): FetchAllCharactersDbUseCase {
    override fun execute(): List<Character> {
        TODO("Not yet implemented")
    }

}