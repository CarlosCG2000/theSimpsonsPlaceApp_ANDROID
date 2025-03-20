package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode

interface getEpisodeByIdUseCase {
    fun execute(id: String): Episode?
}