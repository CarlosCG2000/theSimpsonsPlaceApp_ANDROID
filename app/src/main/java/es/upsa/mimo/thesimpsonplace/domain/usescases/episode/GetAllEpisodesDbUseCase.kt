package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

import es.upsa.mimo.thesimpsonplace.domain.models.Episode
import kotlinx.coroutines.flow.Flow

interface GetAllEpisodesDbUseCase {
    // Si tu función ya devuelve un Flow, no necesitás que el operador invoke() sea suspend, porque el Flow es asíncrono por sí mismo.
    operator fun invoke(): Flow<List<Episode>>
}