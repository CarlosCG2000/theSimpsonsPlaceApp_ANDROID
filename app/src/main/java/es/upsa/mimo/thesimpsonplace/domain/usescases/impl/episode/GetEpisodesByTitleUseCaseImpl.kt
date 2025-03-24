package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesByTitleUseCase
import javax.inject.Inject

class GetEpisodesByTitleUseCaseImpl  @Inject constructor(val repository: EpisodeRepository):GetEpisodesByTitleUseCase {

    override suspend fun execute(title: String): List<Episode> = repository.getEpisodesByTitle(title)

}