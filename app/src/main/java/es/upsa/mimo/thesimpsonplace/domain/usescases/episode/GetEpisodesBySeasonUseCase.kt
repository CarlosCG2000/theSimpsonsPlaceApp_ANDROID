package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode

interface GetEpisodesBySeasonUseCase {
    suspend fun execute(season: Int,
                        episode: List<Episode> = emptyList()): List<Episode>
}