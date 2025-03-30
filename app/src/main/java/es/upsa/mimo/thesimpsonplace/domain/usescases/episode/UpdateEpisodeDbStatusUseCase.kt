package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

interface UpdateEpisodeDbStatusUseCase {
    suspend fun execute(episodeId: String, esVisto: Boolean, esFavorito: Boolean)
}