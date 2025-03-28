package es.upsa.mimo.thesimpsonplace.data.sources.database

import kotlinx.coroutines.flow.Flow

interface GameDatastoreDao {
    val gameStatsFlow: Flow<Pair<Int, Int>>
    suspend fun updateStats(aciertos: Int, preguntas: Int)
    suspend fun resetStats()
}