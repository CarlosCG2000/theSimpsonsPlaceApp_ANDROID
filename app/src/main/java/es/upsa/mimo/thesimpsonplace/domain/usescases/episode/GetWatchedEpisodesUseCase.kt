package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode
import kotlinx.coroutines.flow.Flow

interface GetWatchedEpisodesUseCase {
    suspend operator fun invoke(): Flow<List<Episode>>
}