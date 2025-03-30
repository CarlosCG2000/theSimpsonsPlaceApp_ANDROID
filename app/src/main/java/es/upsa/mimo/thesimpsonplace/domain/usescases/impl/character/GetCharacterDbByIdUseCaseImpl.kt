package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character

import es.upsa.mimo.thesimpsonplace.domain.entities.Character
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetAllCharactersDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetCharacterDbByIdUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterDbByIdUseCaseImpl @Inject constructor(val repository: CharaterRepository): GetCharacterDbByIdUseCase {
    override suspend fun execute(id: Int): Character? = repository.getCharacterDbById(id)
}
