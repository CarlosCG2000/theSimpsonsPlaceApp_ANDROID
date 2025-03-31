package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.game

import es.upsa.mimo.thesimpsonplace.domain.repository.GameRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.game.UpdateStatsUseCase
import javax.inject.Inject

class UpdateStatsUseCaseImpl @Inject constructor(val repository: GameRepository): UpdateStatsUseCase {
    override suspend operator fun invoke(aciertos: Int, preguntas: Int) =
        repository.updateStats(aciertos, preguntas)
    }