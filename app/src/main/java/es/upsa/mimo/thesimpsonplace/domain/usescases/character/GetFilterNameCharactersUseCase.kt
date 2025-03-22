package es.upsa.mimo.thesimpsonplace.domain.usescases.character

import android.content.Context
import es.upsa.mimo.thesimpsonplace.domain.entities.Character

interface GetFilterNameCharactersUseCase {
    suspend fun execute(name:String): List<Character>
}