package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode

interface GetEpisodesBySeasonUseCase {
    suspend operator fun invoke(season: Int,
                                episode: List<Episode> = emptyList()): List<Episode>
}