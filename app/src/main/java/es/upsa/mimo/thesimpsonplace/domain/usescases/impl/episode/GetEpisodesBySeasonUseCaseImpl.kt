package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeDto
import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesBySeasonUseCase
import javax.inject.Inject

class GetEpisodesBySeasonUseCaseImpl  @Inject constructor(val repository: EpisodeRepository):GetEpisodesBySeasonUseCase {
    override suspend fun execute(season: Int,
                                 episodes: List<Episode>): List<Episode>
    = repository.getEpisodesBySeason(season, episodes)
}