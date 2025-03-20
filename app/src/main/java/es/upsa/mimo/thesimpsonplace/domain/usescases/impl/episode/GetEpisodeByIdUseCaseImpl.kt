package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodeByIdUseCase

class GetEpisodeByIdUseCaseImpl(val repository: EpisodeRepository):GetEpisodeByIdUseCase {

    override /*suspend*/ fun execute(id: String): Episode? = repository.getEpisodeById(id)

}