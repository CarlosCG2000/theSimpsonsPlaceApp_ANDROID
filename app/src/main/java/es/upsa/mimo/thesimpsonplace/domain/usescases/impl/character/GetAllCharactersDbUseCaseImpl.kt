package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character

import es.upsa.mimo.thesimpsonplace.domain.entities.Character
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetAllCharactersDbUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCharactersDbUseCaseImpl  @Inject constructor(val repository: CharaterRepository): GetAllCharactersDbUseCase {
    override suspend fun execute(): Flow<List<Character>> = repository.getAllCharactersDb()
}

