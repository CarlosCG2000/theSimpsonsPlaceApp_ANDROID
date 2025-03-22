package es.upsa.mimo.thesimpsonplace.domain.usescases.character

interface UpdateCharacterDbUseCase {
    suspend fun execute(id: Int): Unit
}