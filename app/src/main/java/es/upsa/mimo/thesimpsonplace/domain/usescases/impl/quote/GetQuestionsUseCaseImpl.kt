package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.quote

import es.upsa.mimo.thesimpsonplace.domain.models.Question
import es.upsa.mimo.thesimpsonplace.domain.mappers.toQuestion
import es.upsa.mimo.thesimpsonplace.domain.repository.QuoteRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.GetQuestionsUseCase
import javax.inject.Inject

class GetQuestionsUseCaseImpl @Inject constructor(val repository: QuoteRepository): GetQuestionsUseCase {
    override suspend operator fun invoke(): List<Question> =
         repository.getQuotes().map { it.toQuestion() }
}