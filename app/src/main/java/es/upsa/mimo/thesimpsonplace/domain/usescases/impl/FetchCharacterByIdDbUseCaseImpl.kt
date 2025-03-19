package es.upsa.mimo.thesimpsonplace.domain.usescases.impl

import es.upsa.mimo.thesimpsonplace.domain.entities.Character
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.FetchCharacterByIdDbUseCase

class FetchCharacterByIdDbUseCaseImpl(val repository: CharaterRepository): FetchCharacterByIdDbUseCase {
    override fun execute(id: Int): Character {
        return repository.getCharacterByIdDb(id = id)
    }


}