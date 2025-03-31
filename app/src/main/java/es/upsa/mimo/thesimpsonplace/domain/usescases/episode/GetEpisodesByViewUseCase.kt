package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode

interface GetEpisodesByViewUseCase {
    suspend fun execute(isView: Boolean, episodes: List<Episode>): List<Episode>
}