package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode

interface GetEpisodeByIdUseCase {
     suspend operator fun invoke(id: String): Episode?
}