package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

interface IsEpisodeDbFavoriteUseCase {
    suspend operator fun invoke(episodeId: String): Boolean?
}