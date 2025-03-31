package es.upsa.mimo.thesimpsonplace.domain.repository

import kotlinx.coroutines.flow.Flow

interface GameRepository {
    val gameStatsFlow: Flow<Pair<Int, Int>>
    suspend fun updateStats(aciertos: Int, preguntas: Int)
    suspend fun resetStats()
}