package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character

import es.upsa.mimo.thesimpsonplace.domain.models.Character
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetCharacterDbByIdUseCase
import javax.inject.Inject

class GetCharacterDbByIdUseCaseImpl @Inject constructor(val repository: CharaterRepository): GetCharacterDbByIdUseCase {
    override suspend operator fun invoke(id: Int): Character? =
        repository.getCharacterDbById(id)
}
