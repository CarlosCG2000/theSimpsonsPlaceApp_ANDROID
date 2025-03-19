package es.upsa.mimo.thesimpsonplace.domain.usescases

interface UpdateCharacterDbUseCase {
    fun execute(id: Int): Unit
}