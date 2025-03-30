package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetAllEpisodesDbUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllEpisodesDbUseCaseImpl  @Inject constructor(val repository: EpisodeRepository): GetAllEpisodesDbUseCase{
    override suspend fun execute(): Flow<List<Episode>> = repository.getAllEpisodesDb()
}