package es.upsa.mimo.thesimpsonplace.domain.usescases.quote


interface DeleteQuoteDbUseCase {
    suspend fun execute(cita: String)
    // operator fun invoke(cita: String) --> OTRA OPCIÓN DE DEFINIRLO
}