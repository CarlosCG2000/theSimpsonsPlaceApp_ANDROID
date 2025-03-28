package es.upsa.mimo.thesimpsonplace.domain.usescases.user

import es.upsa.mimo.thesimpsonplace.data.entities.user.UserPreference
import kotlinx.coroutines.flow.Flow

interface GetUserPreferencesUseCase {
    val userPreferencesFlow: Flow<UserPreference>
}