package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode

interface getEpisodesBySeasonUseCase {
    fun execute(season:Int): List<Episode>
}