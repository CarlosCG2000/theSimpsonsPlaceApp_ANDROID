package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeDto
import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import java.util.Date

interface GetEpisodesByDateUseCase {
    suspend fun execute(minDate: Date?, maxDate: Date?,
                        episode: List<Episode> = emptyList()): List<Episode>
}