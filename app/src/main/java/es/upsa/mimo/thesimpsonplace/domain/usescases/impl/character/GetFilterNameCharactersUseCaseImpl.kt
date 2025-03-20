package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character

import es.upsa.mimo.thesimpsonplace.domain.entities.Character
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetFilterNameCharactersUseCase

class GetFilterNameCharactersUseCaseImpl(val repository: CharaterRepository): GetFilterNameCharactersUseCase {

    override /*suspend*/ fun execute(name: String): List<Character> {
        return repository.getCharactersByName(name = name)
    }

}