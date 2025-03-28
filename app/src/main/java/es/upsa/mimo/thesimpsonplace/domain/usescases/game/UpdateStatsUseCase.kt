package es.upsa.mimo.thesimpsonplace.domain.usescases.game

interface UpdateStatsUseCase {
    suspend fun execute(aciertos: Int, preguntas: Int): Unit
}