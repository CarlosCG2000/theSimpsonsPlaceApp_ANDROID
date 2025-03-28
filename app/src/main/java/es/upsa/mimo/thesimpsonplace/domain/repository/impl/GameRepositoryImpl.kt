package es.upsa.mimo.thesimpsonplace.domain.repository.impl

import es.upsa.mimo.thesimpsonplace.data.sources.database.GameDatastoreDao
import es.upsa.mimo.thesimpsonplace.data.sources.database.impl.GameDatastoreDaoImpl
import es.upsa.mimo.thesimpsonplace.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(private val dataSource: GameDatastoreDao): GameRepository {

    override val gameStatsFlow: Flow<Pair<Int, Int>> = dataSource.gameStatsFlow

    override suspend fun updateStats(aciertos: Int, preguntas: Int) {
        dataSource.updateStats(aciertos, preguntas)
    }

    override suspend fun resetStats() {
        dataSource.resetStats()
    }
}