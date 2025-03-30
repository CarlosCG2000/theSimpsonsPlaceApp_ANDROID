package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.UpdateEpisodeDbStatusUseCase
import javax.inject.Inject

class UpdateEpisodeDbStatusUseCaseImpl  @Inject constructor(val repository: EpisodeRepository): UpdateEpisodeDbStatusUseCase {
    override suspend fun execute(
        episodeId: String,
        esVisto: Boolean,
        esFavorito: Boolean
    ) = repository.updateEpisodeDbStatus(episodeId, esVisto, esFavorito)

}