package es.upsa.mimo.thesimpsonplace.data.daos.local

import es.upsa.mimo.thesimpsonplace.data.entities.user.UserPreference
import kotlinx.coroutines.flow.Flow

interface UserDatastoreDao {
    val userPreferencesFlow: Flow< UserPreference >
    suspend fun updateUser(user: UserPreference)
}
