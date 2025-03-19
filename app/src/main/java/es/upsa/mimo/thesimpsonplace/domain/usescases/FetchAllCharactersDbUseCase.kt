package es.upsa.mimo.thesimpsonplace.domain.usescases

import es.upsa.mimo.thesimpsonplace.domain.entities.Character

interface FetchAllCharactersDbUseCase {
    fun execute(): List<Character>
}