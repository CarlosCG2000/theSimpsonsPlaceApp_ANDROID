package es.upsa.mimo.thesimpsonplace.domain.usescases.quote

import es.upsa.mimo.thesimpsonplace.domain.models.Quote
import kotlinx.coroutines.flow.Flow

interface GetAllQuoteDbUseCase {
    operator fun invoke(): Flow<List<Quote>>
}