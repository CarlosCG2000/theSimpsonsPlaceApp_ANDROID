package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodeDbByIdUseCase
import javax.inject.Inject

class GetEpisodeDbByIdUseCaseImpl @Inject constructor(val repository: EpisodeRepository): GetEpisodeDbByIdUseCase {
    override suspend operator fun invoke(episodeId: String): Episode? =
        repository.getEpisodeDbById(episodeId)
}