package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode

interface GetEpisodesByChapterUseCase {
    suspend operator fun invoke(chapter:Int,
                                episode: List<Episode> = emptyList()): List<Episode>
}