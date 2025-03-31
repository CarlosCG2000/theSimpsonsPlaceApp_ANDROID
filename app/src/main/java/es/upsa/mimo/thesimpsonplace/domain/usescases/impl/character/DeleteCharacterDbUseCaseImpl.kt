package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character

import es.upsa.mimo.thesimpsonplace.domain.models.Character
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.DeleteCharacterDbUseCase
import javax.inject.Inject

class DeleteCharacterDbUseCaseImpl @Inject constructor(val repository: CharaterRepository): DeleteCharacterDbUseCase {
    override suspend operator fun invoke(character: Character) =
        repository.deleteCharacterDb(character)
}