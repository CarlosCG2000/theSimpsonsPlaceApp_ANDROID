package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.getAllEpisodesUseCase

class getAllEpisodesUseCaseImpl(val repository: EpisodeRepository) :getAllEpisodesUseCase {

    override fun execute(): List<Episode> = repository.getAllEpisodes()

}