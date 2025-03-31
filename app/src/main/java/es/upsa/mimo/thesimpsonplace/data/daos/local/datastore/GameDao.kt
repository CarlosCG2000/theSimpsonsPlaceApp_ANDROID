package es.upsa.mimo.thesimpsonplace.data.daos.local.datastore

import kotlinx.coroutines.flow.Flow

interface GameDao {
    val gameStatsFlow: Flow<Pair<Int, Int>>
    suspend fun updateStats(aciertos: Int, preguntas: Int)
    suspend fun resetStats()
}