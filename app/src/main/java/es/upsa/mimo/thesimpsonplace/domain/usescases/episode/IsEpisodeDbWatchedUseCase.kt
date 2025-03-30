package es.upsa.mimo.thesimpsonplace.domain.usescases.episode


interface IsEpisodeDbWatchedUseCase {
    suspend fun execute(episodeId: String): Boolean?
}