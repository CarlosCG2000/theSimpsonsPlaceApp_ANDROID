package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetAllEpisodesDbUseCase

class GetAllEpisodesDbUseCaseImpl(val repository: EpisodeRepository): GetAllEpisodesDbUseCase{
    override /*suspend*/ fun execute(): List<Episode> = repository.getAllEpisodesDb()
}