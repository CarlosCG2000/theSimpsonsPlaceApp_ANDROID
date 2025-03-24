package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodeByIdsDbUseCase
import javax.inject.Inject

class GetEpisodeByIdsDbUseCaseImpl  @Inject constructor(val repository: EpisodeRepository): GetEpisodeByIdsDbUseCase {

    override suspend fun execute(ids: List<String>): List<Episode>? = repository.getEpisodeByIdsDb(ids)

}