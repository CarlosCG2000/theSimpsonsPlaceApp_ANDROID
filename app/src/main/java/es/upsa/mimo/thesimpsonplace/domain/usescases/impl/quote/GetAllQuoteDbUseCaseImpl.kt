package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.quote

import es.upsa.mimo.thesimpsonplace.domain.models.Quote
import es.upsa.mimo.thesimpsonplace.domain.repository.QuoteRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.GetAllQuoteDbUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllQuoteDbUseCaseImpl @Inject constructor(val repository: QuoteRepository): GetAllQuoteDbUseCase {
    override suspend operator fun invoke(): Flow<List<Quote>> =
        repository.getAllQuotesDb()
}