package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode

interface GetEpisodesOrderUseCase {
    suspend operator fun invoke(isAscendent: Boolean,
                                episodes: List<Episode>): List<Episode>
}