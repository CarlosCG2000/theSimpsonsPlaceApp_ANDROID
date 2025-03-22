package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesBySeasonUseCase

class GetEpisodesBySeasonUseCaseImpl(val repository: EpisodeRepository):GetEpisodesBySeasonUseCase {
    override suspend fun execute(season: Int): List<Episode> = repository.getEpisodesBySeason(season)
}