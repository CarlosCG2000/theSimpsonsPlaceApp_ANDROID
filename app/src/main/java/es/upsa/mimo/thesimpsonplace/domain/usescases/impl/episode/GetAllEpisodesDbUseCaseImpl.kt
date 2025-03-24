package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetAllEpisodesDbUseCase
import javax.inject.Inject

class GetAllEpisodesDbUseCaseImpl  @Inject constructor(val repository: EpisodeRepository): GetAllEpisodesDbUseCase{
    override suspend fun execute(): List<Episode> = repository.getAllEpisodesDb()
}