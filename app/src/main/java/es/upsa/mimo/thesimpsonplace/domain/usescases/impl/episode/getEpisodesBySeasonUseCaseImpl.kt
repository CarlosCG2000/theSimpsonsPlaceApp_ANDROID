package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.getEpisodesBySeasonUseCase

class getEpisodesBySeasonUseCaseImpl(val repository: EpisodeRepository):getEpisodesBySeasonUseCase {
    override fun execute(season: Int): List<Episode> = repository.getEpisodesBySeason(season)
}