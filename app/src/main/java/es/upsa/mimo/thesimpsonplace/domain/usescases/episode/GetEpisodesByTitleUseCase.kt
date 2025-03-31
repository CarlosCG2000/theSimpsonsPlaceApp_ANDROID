package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode

interface GetEpisodesByTitleUseCase {
    suspend fun execute(title: String,
                        episode: List<Episode> = emptyList()): List<Episode>
}