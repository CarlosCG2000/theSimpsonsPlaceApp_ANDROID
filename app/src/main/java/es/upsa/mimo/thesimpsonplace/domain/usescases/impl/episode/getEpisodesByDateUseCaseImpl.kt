package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.getEpisodesByDateUseCase
import java.util.Date

class getEpisodesByDateUseCaseImpl(val repository: EpisodeRepository):getEpisodesByDateUseCase {

    override fun execute(
        minDate: Date?,
        maxDate: Date?
    ): List<Episode> = repository.getEpisodesByDate(minDate, maxDate)

}