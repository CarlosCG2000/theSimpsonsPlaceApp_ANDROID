package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesOrderUseCase
import javax.inject.Inject

class GetEpisodesOrderUseCaseImpl @Inject constructor(val repository: EpisodeRepository): GetEpisodesOrderUseCase {
    override suspend fun execute(isAscendent: Boolean, episodes: List<Episode>): List<Episode>
                    = repository.getEpisodesOrder( isAscendent, episodes)
}