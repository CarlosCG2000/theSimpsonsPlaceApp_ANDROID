package es.upsa.mimo.thesimpsonplace.domain.usescases.impl

import es.upsa.mimo.thesimpsonplace.domain.entities.Character
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.GetFilterNameCharactersUseCase

class GetFilterNameCharactersUseCaseImpl(val repository: CharaterRepository): GetFilterNameCharactersUseCase {

    override fun execute(name: String): List<Character> {
        return repository.getFilterNameCharacters(name = name)
    }

}