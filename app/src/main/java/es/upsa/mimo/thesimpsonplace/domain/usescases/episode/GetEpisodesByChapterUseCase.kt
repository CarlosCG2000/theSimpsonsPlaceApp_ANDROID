package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode

interface GetEpisodesByChapterUseCase {
    suspend fun execute(chapter:Int,
                        episode: List<Episode> = emptyList()): List<Episode>
}