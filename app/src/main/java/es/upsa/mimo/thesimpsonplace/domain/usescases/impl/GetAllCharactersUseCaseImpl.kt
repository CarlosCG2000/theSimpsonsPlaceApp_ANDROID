package es.upsa.mimo.thesimpsonplace.domain.usescases.impl

import es.upsa.mimo.thesimpsonplace.domain.entities.Character
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.GetAllCharactersUseCase

class GetAllCharactersUseCaseImpl(val repository: CharaterRepository): GetAllCharactersUseCase {

    override fun execute(): List<Character> {
        return repository.getAllCharacters()
    }

}