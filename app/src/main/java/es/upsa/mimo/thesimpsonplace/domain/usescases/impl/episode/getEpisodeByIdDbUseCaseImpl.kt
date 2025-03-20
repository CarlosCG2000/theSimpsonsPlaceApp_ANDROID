package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.getEpisodeByIdDbUseCase

class getEpisodeByIdDbUseCaseImpl(val repository: EpisodeRepository): getEpisodeByIdDbUseCase {
    override fun execute(id: String): Episode? = repository.getEpisodeByIdDb(id)
}