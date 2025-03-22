package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character

import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.UpdateCharacterDbUseCase

class UpdateCharacterDbUseCaseImpl(val repository: CharaterRepository): UpdateCharacterDbUseCase {

    override suspend fun execute(id: Int) {
        repository.deleteCharacterDb(id = id)
    }

}