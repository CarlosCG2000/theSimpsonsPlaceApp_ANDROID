package es.upsa.mimo.thesimpsonplace.domain.usescases.episode


interface IsEpisodeDbWatchedUseCase {
    suspend operator fun invoke(episodeId: String): Boolean?
}