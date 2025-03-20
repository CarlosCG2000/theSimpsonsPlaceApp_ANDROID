package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.getEpisodesByTitleUseCase

class getEpisodesByTitleUseCaseImpl(val repository: EpisodeRepository):getEpisodesByTitleUseCase {

    override fun execute(title: String): List<Episode> = repository.getEpisodesByTitle(title)

}