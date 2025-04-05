package es.upsa.mimo.thesimpsonplace.useCasesMockFake

import es.upsa.mimo.thesimpsonplace.domain.models.Character
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.InsertCharacterDbUseCase
import es.upsa.mimo.thesimpsonplace.charactersFake

class InsertCharacterDbUseCaseMock : InsertCharacterDbUseCase {
    override suspend fun invoke(character: Character) {
        charactersFake.add(character)
    }
}
