package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesByViewUseCase
import javax.inject.Inject

class GetEpisodesByViewUseCaseImpl @Inject constructor(val repository: EpisodeRepository): GetEpisodesByViewUseCase {
    override suspend operator fun invoke(isView: Boolean,
                                         episodes: List<Episode>): List<Episode> =
        repository.getEpisodesByView(isView, episodes)
}