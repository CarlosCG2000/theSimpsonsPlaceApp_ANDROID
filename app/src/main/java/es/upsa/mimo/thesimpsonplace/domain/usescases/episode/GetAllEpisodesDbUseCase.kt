package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import kotlinx.coroutines.flow.Flow

interface GetAllEpisodesDbUseCase {
    suspend fun execute(): Flow<List<Episode>>
}