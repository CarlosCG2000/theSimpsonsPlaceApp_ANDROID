package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode

interface insertEpisodeDbUseCase {
    fun execute(episode: Episode, esView: Boolean, isFav: Boolean): Unit
}