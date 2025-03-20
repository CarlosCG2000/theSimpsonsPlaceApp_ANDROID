package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode

interface getEpisodeByIdsDbUseCase {
    fun execute(ids: List<String>): List<Episode>?
}