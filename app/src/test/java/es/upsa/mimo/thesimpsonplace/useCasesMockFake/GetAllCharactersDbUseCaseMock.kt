package es.upsa.mimo.thesimpsonplace.useCasesMockFake

import es.upsa.mimo.thesimpsonplace.domain.models.Character
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetAllCharactersDbUseCase
import es.upsa.mimo.thesimpsonplace.charactersFake
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllCharactersDbUseCaseMock : GetAllCharactersDbUseCase {
    override fun invoke(): Flow<List<Character>> {
        return flow {
            emit(charactersFake)
        }
    }
}
