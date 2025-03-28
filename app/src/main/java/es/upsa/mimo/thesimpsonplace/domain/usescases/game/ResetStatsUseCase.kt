package es.upsa.mimo.thesimpsonplace.domain.usescases.game

interface ResetStatsUseCase {
    suspend fun execute(): Unit
}