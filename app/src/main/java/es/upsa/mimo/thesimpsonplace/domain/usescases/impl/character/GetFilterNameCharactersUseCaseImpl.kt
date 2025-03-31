package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character

import es.upsa.mimo.thesimpsonplace.domain.models.Character
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetFilterNameCharactersUseCase
import javax.inject.Inject

class GetFilterNameCharactersUseCaseImpl  @Inject constructor(val repository: CharaterRepository): GetFilterNameCharactersUseCase {

    override suspend fun execute(name: String): List<Character> {
        return repository.getCharactersByName(name = name)
    }

}