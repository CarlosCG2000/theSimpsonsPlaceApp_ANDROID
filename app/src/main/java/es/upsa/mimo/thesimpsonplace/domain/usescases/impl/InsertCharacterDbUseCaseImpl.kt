package es.upsa.mimo.thesimpsonplace.domain.usescases.impl

import es.upsa.mimo.thesimpsonplace.domain.entities.Character
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.InsertCharacterDbUseCase

class InsertCharacterDbUseCaseImpl(val repository: CharaterRepository): InsertCharacterDbUseCase {

    override fun execute(character: Character) {
        repository.insertCharacterDb(character)
    }

}