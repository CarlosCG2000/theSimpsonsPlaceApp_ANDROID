package es.upsa.mimo.thesimpsonplace.domain.usescases.episode

interface updateEpisodeDbUseCase {
    fun execute(id: String, esView: Boolean, isFav: Boolean): Unit
}