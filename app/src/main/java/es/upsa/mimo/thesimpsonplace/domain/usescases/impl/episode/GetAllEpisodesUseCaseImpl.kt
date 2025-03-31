package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetAllEpisodesUseCase
import javax.inject.Inject

class GetAllEpisodesUseCaseImpl  @Inject constructor(val repository: EpisodeRepository) :GetAllEpisodesUseCase {
    override suspend operator fun invoke(): List<Episode> =
                repository.getAllEpisodes()
}