package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodeByIdDbUseCase

class GetEpisodeByIdDbUseCaseImpl(val repository: EpisodeRepository): GetEpisodeByIdDbUseCase {
    override /*suspend*/ fun execute(id: String): Episode? = repository.getEpisodeByIdDb(id)
}