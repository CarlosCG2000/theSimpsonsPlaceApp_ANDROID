package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.quote

import es.upsa.mimo.thesimpsonplace.domain.entities.Question
import es.upsa.mimo.thesimpsonplace.domain.mappers.toQuestion
import es.upsa.mimo.thesimpsonplace.domain.repository.QuoteRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.GetQuestionsUseCase
import javax.inject.Inject

class GetQuestionsUseCaseImpl  @Inject constructor(val repository: QuoteRepository): GetQuestionsUseCase {

    override suspend fun execute(): List<Question> {
        return repository.getQuotes().map { it.toQuestion() }
    }

}