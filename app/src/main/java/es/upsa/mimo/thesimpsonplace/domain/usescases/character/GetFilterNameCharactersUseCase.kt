package es.upsa.mimo.thesimpsonplace.domain.usescases.character

import es.upsa.mimo.thesimpsonplace.domain.models.Character

interface GetFilterNameCharactersUseCase {
    suspend fun execute(name:String): List<Character>
}