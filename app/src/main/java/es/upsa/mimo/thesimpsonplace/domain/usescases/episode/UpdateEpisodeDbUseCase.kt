package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

interface UpdateEpisodeDbUseCase {
    suspend fun execute(id: String, esView: Boolean, isFav: Boolean): Unit
}