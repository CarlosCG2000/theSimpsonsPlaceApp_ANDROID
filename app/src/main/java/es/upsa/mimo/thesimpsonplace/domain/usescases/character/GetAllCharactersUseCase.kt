package es.upsa.mimo.thesimpsonplace.domain.usescases.character

import es.upsa.mimo.thesimpsonplace.domain.entities.Character

interface GetAllCharactersUseCase {
    fun execute(): List<Character>
}