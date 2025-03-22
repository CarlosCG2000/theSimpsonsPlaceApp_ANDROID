package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetAllEpisodesUseCase

class GetAllEpisodesUseCaseImpl(val repository: EpisodeRepository) :GetAllEpisodesUseCase {

    override suspend fun execute(): List<Episode> = repository.getAllEpisodes()

}