package es.upsa.mimo.thesimpsonplace.domain.usescases.impl

import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.UpdateCharacterDbUseCase

class UpdateCharacterDbUseCaseImpl(val repository: CharaterRepository): UpdateCharacterDbUseCase {

    override fun execute(id: Int) {
        repository.updateCharacterDb(id = id)
    }

}