package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesByChapterUseCase
import javax.inject.Inject

class GetEpisodesByChapterUseCaseImpl  @Inject constructor(val repository: EpisodeRepository):GetEpisodesByChapterUseCase {
    override suspend fun execute(chapter: Int): List<Episode> = repository.getEpisodesByChapter(chapter)
}