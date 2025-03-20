package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.getAllEpisodesDbUseCase

class getAllEpisodesDbUseCaseImpl(val repository: EpisodeRepository): getAllEpisodesDbUseCase{
    override fun execute(): List<Episode> = repository.getAllEpisodesDb()
}