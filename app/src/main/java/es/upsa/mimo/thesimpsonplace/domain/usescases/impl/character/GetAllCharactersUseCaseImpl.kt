package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character

import es.upsa.mimo.thesimpsonplace.domain.models.Character
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetAllCharactersUseCase
import javax.inject.Inject

class GetAllCharactersUseCaseImpl @Inject constructor(
                                            private val repository: CharaterRepository
                                            ): GetAllCharactersUseCase {
    override suspend operator fun invoke(): List<Character> =
         repository.getAllCharacters()
}