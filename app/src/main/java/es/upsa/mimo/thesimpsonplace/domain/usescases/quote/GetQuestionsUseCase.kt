package es.upsa.mimo.thesimpsonplace.domain.usescases.quote

import es.upsa.mimo.thesimpsonplace.domain.entities.Question

interface GetQuestionsUseCase {
    fun execute(): List<Question>
}