package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode

interface GetEpisodesOrderUseCase {
    suspend fun execute(isAscendent: Boolean, episodes: List<Episode>): List<Episode>
}