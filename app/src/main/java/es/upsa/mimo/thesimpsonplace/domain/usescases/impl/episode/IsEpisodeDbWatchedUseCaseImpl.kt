package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.IsEpisodeDbWatchedUseCase
import javax.inject.Inject

class IsEpisodeDbWatchedUseCaseImpl @Inject constructor(val repository: EpisodeRepository): IsEpisodeDbWatchedUseCase {
    override suspend fun execute(episodeId: String): Boolean? = repository.isEpisodeDbWatched(episodeId)
}