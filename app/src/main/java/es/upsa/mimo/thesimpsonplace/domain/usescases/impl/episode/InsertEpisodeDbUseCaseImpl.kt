package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.InsertEpisodeDbUseCase
import javax.inject.Inject

class InsertEpisodeDbUseCaseImpl  @Inject constructor(val repository: EpisodeRepository): InsertEpisodeDbUseCase {

    override suspend fun execute(
        episode: Episode,
        esView: Boolean,
        isFav: Boolean
    ) = repository.insertEpisodeDb(episode, esView, isFav)

}