package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodeByIdUseCase
import javax.inject.Inject

class GetEpisodeByIdUseCaseImpl  @Inject constructor(val repository: EpisodeRepository):GetEpisodeByIdUseCase {

    override suspend fun execute(id: String): Episode? = repository.getEpisodeById(id)

}