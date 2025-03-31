package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.IsEpisodeDbFavoriteUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.IsEpisodeDbWatchedUseCase
import javax.inject.Inject

class IsEpisodeDbFavoriteUseCaseImpl @Inject constructor(val repository: EpisodeRepository): IsEpisodeDbFavoriteUseCase {
    override suspend operator fun invoke(episodeId: String): Boolean? =
        repository.isEpisodeDbFavorite(episodeId)
}