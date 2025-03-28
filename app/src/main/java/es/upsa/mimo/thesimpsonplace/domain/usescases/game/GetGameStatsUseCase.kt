package es.upsa.mimo.thesimpsonplace.domain.usescases.game

import kotlinx.coroutines.flow.Flow

interface GetGameStatsUseCase {
    val gameStatsFlow: Flow<Pair<Int, Int>>
}