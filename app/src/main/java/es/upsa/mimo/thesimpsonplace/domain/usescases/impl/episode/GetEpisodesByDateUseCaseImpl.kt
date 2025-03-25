package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeDto
import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesByDateUseCase
import java.util.Date
import javax.inject.Inject

class GetEpisodesByDateUseCaseImpl  @Inject constructor(val repository: EpisodeRepository):GetEpisodesByDateUseCase {

    override suspend fun execute(
        minDate: Date?,
        maxDate: Date?,
        episodes: List<Episode>
    ): List<Episode> = repository.getEpisodesByDate(minDate, maxDate, episodes)

}