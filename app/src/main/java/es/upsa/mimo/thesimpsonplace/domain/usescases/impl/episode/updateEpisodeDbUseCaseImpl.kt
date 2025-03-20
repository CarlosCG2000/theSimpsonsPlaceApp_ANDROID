package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.updateEpisodeDbUseCase

class updateEpisodeDbUseCaseImpl(val repository: EpisodeRepository): updateEpisodeDbUseCase {
    override fun execute(id: String, esView: Boolean, isFav: Boolean) = repository.updateEpisodeDb(id, esView, isFav)
}