package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode

interface InsertEpisodeDbUseCase {
    suspend fun execute(episode: Episode)
}