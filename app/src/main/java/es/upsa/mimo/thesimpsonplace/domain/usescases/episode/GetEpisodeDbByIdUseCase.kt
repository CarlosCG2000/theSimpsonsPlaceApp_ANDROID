package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode

interface GetEpisodeDbByIdUseCase {
    suspend fun execute(episodeId: String): Episode?
}