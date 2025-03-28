package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.user

import es.upsa.mimo.thesimpsonplace.data.entities.user.UserPreference
import es.upsa.mimo.thesimpsonplace.domain.repository.GameRepository
import es.upsa.mimo.thesimpsonplace.domain.repository.UserRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.user.GetUserPreferencesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserPreferencesUseCaseImpl @Inject constructor(val repository: UserRepository): GetUserPreferencesUseCase {
    override val userPreferencesFlow: Flow<UserPreference>
        get() = repository.userPreferencesFlow
}