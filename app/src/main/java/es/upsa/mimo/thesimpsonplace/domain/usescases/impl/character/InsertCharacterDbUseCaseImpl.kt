package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character

import es.upsa.mimo.thesimpsonplace.domain.models.Character
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.InsertCharacterDbUseCase
import javax.inject.Inject

class InsertCharacterDbUseCaseImpl  @Inject constructor(val repository: CharaterRepository): InsertCharacterDbUseCase {
    override suspend fun execute(character: Character) = repository.insertCharacterDb(character)
}