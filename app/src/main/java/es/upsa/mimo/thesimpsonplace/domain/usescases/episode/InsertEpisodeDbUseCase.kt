package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode

interface InsertEpisodeDbUseCase {
    suspend operator fun invoke(episode: Episode)
}