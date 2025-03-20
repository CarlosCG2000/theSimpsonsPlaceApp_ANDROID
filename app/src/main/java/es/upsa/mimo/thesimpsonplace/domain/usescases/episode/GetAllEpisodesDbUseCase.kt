package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.entities.Episode

interface GetAllEpisodesDbUseCase {
    /*suspend*/ fun execute(): List<Episode>
}