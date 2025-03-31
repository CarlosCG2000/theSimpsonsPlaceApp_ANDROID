package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetWatchedEpisodesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWatchedEpisodesUseCaseImpl @Inject constructor(val repository: EpisodeRepository): GetWatchedEpisodesUseCase {
    override suspend fun execute(): Flow<List<Episode>> = repository.getWatchedEpisodes()
}