package es.upsa.mimo.thesimpsonplace.data.daos.local.datastore

import es.upsa.mimo.thesimpsonplace.data.entities.user.UserPreference
import kotlinx.coroutines.flow.Flow

interface UserDao {
    val userPreferencesFlow: Flow<UserPreference>
    suspend fun updateUser(user: UserPreference)
}