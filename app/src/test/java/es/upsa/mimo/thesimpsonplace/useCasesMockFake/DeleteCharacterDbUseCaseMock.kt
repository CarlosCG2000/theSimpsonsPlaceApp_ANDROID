package es.upsa.mimo.thesimpsonplace.useCasesMockFake

import es.upsa.mimo.thesimpsonplace.domain.models.Character
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.DeleteCharacterDbUseCase
import es.upsa.mimo.thesimpsonplace.charactersFake

class DeleteCharacterDbUseCaseMock : DeleteCharacterDbUseCase {
    override suspend fun invoke(character: Character) {
        charactersFake.removeIf { it.id == character.id }
    }
}
