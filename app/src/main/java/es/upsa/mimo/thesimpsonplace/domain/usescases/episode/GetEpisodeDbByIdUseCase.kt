package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode

interface GetEpisodeDbByIdUseCase {
    suspend operator fun invoke(episodeId: String): Episode?
}