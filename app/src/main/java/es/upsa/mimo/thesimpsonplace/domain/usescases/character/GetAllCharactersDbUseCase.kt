package es.upsa.mimo.thesimpsonplace.domain.usescases.character

import es.upsa.mimo.thesimpsonplace.domain.models.Character
import kotlinx.coroutines.flow.Flow

interface GetAllCharactersDbUseCase {
     operator fun invoke(): Flow<List<Character>>
    // suspend fun execute(): Flow<List<Character>>
}
