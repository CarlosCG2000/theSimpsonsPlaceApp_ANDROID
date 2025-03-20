package es.upsa.mimo.thesimpsonplace.domain.usescases.character

interface UpdateCharacterDbUseCase {
    fun execute(id: Int): Unit
}