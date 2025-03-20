package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.getEpisodeByIdUseCase

class getEpisodeByIdUseCaseImpl(val repository: EpisodeRepository):getEpisodeByIdUseCase {

    override fun execute(id: String): Episode? = repository.getEpisodeById(id)

}