package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode
import kotlinx.coroutines.flow.Flow

interface GetAllEpisodesDbUseCase {
    suspend operator fun invoke(): Flow<List<Episode>>
}