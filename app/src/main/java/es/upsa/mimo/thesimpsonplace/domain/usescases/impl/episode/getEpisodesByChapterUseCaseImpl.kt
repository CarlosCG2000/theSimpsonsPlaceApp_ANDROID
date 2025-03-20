package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.getEpisodesByChapterUseCase

class getEpisodesByChapterUseCaseImpl(val repository: EpisodeRepository):getEpisodesByChapterUseCase {
    override fun execute(chapter: Int): List<Episode> = repository.getEpisodesByChapter(chapter)
}