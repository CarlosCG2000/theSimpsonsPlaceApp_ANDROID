package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.getEpisodeByIdsDbUseCase

class getEpisodeByIdsDbUseCaseImpl(val repository: EpisodeRepository): getEpisodeByIdsDbUseCase {

    override fun execute(ids: List<String>): List<Episode>? = repository.getEpisodeByIdsDb(ids)

}