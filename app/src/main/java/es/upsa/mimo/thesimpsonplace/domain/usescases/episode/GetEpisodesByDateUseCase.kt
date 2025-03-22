package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import java.util.Date

interface GetEpisodesByDateUseCase {
    suspend fun execute(minDate: Date?, maxDate: Date?): List<Episode>
}