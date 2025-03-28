package es.upsa.mimo.thesimpsonplace.domain.repository

import es.upsa.mimo.thesimpsonplace.data.entities.user.UserPreference
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val userPreferencesFlow: Flow< UserPreference >
    suspend fun updateUser(user: UserPreference)
}