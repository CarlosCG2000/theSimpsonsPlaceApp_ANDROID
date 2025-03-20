package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode

interface getEpisodesByChapterUseCase {
    fun execute(chapter:Int): List<Episode>
}