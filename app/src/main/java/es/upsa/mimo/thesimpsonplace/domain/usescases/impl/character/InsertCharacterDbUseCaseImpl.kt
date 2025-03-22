package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character

import es.upsa.mimo.thesimpsonplace.domain.entities.Character
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.InsertCharacterDbUseCase

class InsertCharacterDbUseCaseImpl(val repository: CharaterRepository): InsertCharacterDbUseCase {

    override suspend fun execute(character: Character) {
        repository.insertCharacterDb(character)
    }

}