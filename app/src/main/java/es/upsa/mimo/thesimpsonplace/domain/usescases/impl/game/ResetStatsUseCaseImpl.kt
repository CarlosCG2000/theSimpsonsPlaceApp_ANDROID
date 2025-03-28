package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.game

import es.upsa.mimo.thesimpsonplace.domain.repository.GameRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.game.ResetStatsUseCase
import javax.inject.Inject

class ResetStatsUseCaseImpl @Inject constructor(val repository: GameRepository): ResetStatsUseCase{
    override suspend fun execute() {
        repository.resetStats()
    }
}