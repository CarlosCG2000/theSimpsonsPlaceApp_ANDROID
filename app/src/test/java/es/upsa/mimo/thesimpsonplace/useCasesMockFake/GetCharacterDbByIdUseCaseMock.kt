package es.upsa.mimo.thesimpsonplace.useCasesMockFake

import es.upsa.mimo.thesimpsonplace.domain.models.Character
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetCharacterDbByIdUseCase
import es.upsa.mimo.thesimpsonplace.charactersFake

class GetCharacterDbByIdUseCaseMock : GetCharacterDbByIdUseCase {
    override suspend fun invoke(id: Int): Character? {
        return charactersFake.find { it.id == id }
    }
}
