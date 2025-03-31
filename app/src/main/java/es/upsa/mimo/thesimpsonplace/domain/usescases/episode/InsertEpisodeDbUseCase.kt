package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode

interface InsertEpisodeDbUseCase {
    suspend fun execute(episode: Episode)
}