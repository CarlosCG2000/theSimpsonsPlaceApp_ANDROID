package es.upsa.mimo.thesimpsonplace.domain.usescases.game

interface UpdateStatsUseCase {
    suspend operator fun invoke(aciertos: Int,
                                preguntas: Int)
}