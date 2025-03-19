package es.upsa.mimo.thesimpsonplace.domain.usescases

import es.upsa.mimo.thesimpsonplace.domain.entities.Character

interface FetchCharacterByIdDbUseCase {
    fun execute(id: Int): Character
}