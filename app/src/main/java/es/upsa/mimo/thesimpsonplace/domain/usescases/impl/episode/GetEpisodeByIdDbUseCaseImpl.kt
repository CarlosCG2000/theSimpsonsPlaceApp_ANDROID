package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodeByIdDbUseCase
import javax.inject.Inject

class GetEpisodeByIdDbUseCaseImpl  @Inject constructor(val repository: EpisodeRepository): GetEpisodeByIdDbUseCase {
    override suspend fun execute(id: String): Episode? = repository.getEpisodeByIdDb(id)
}