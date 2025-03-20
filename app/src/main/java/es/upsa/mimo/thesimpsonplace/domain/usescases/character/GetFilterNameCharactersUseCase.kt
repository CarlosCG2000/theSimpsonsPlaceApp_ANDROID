package es.upsa.mimo.thesimpsonplace.domain.usescases.character

import es.upsa.mimo.thesimpsonplace.domain.entities.Character

interface GetFilterNameCharactersUseCase {
    fun execute(name:String): List<Character>
}