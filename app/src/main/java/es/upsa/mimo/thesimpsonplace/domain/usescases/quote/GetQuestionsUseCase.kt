package es.upsa.mimo.thesimpsonplace.domain.usescases.quote

import es.upsa.mimo.thesimpsonplace.domain.models.Question

interface GetQuestionsUseCase {
    suspend fun execute(): List<Question>
}