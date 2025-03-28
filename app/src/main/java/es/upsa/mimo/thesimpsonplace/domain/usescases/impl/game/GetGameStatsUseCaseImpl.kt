package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.game

import es.upsa.mimo.thesimpsonplace.domain.repository.GameRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.game.GetGameStatsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGameStatsUseCaseImpl @Inject constructor(val repository: GameRepository): GetGameStatsUseCase{
    override val gameStatsFlow: Flow<Pair<Int, Int>>
        get() = repository.gameStatsFlow
}
