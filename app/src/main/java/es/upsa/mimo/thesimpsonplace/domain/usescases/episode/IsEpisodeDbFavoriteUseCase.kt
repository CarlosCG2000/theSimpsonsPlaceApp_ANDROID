package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

interface IsEpisodeDbFavoriteUseCase {
    suspend fun execute(episodeId: String): Boolean?
}