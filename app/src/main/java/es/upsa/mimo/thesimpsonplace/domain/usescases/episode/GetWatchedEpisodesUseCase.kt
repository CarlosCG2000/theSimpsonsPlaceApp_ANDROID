package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import kotlinx.coroutines.flow.Flow

interface GetWatchedEpisodesUseCase {
    suspend fun execute(): Flow<List<Episode>>
}