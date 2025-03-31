package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

interface UpdateEpisodeDbStatusUseCase {
    suspend operator fun invoke(episodeId: String,
                                esVisto: Boolean,
                                esFavorito: Boolean)
}