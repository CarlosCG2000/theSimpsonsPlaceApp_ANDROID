package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode

interface GetAllEpisodesUseCase {
    suspend fun execute(): List<Episode>
}