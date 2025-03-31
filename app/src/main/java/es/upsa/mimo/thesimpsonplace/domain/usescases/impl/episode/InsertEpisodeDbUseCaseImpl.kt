package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.InsertEpisodeDbUseCase
import javax.inject.Inject

class InsertEpisodeDbUseCaseImpl  @Inject constructor(val repository: EpisodeRepository): InsertEpisodeDbUseCase {
    override suspend fun execute(episode: Episode) = repository.insertEpisodeDb(episode)
}