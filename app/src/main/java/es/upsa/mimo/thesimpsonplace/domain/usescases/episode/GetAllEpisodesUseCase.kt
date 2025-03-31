package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode

interface GetAllEpisodesUseCase {
    suspend operator fun invoke(): List<Episode>
}