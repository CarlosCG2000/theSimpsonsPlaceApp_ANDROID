package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode
import java.util.Date

interface GetEpisodesByDateUseCase {
    suspend fun execute(minDate: Date?, maxDate: Date?,
                        episode: List<Episode> = emptyList()): List<Episode>
}