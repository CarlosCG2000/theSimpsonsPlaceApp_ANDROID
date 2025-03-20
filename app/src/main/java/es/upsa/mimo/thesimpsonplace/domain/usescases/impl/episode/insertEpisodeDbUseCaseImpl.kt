package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.insertEpisodeDbUseCase

class insertEpisodeDbUseCaseImpl(val repository: EpisodeRepository): insertEpisodeDbUseCase {

    override fun execute(
        episode: Episode,
        esView: Boolean,
        isFav: Boolean
    ) = repository.insertEpisodeDb(episode, esView, isFav)

}