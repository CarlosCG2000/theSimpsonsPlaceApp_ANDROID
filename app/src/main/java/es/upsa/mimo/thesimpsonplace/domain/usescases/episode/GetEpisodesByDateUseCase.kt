package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode
import java.util.Date

interface GetEpisodesByDateUseCase {
    suspend operator fun invoke(minDate: Date?,
                                maxDate: Date?,
                                episode: List<Episode> = emptyList()): List<Episode>
}