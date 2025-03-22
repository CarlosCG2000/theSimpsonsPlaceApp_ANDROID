package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.UpdateEpisodeDbUseCase

class UpdateEpisodeDbUseCaseImpl(val repository: EpisodeRepository): UpdateEpisodeDbUseCase {
    override suspend fun execute(id: String, esView: Boolean, isFav: Boolean) = repository.updateEpisodeDb(id, esView, isFav)
}