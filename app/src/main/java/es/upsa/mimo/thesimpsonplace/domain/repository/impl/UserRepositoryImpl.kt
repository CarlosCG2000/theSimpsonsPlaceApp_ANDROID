package es.upsa.mimo.thesimpsonplace.domain.repository.impl

import es.upsa.mimo.thesimpsonplace.data.entities.user.UserPreference
import es.upsa.mimo.thesimpsonplace.data.daos.local.UserDatastoreDao
import es.upsa.mimo.thesimpsonplace.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val dataSource: UserDatastoreDao): UserRepository {
    override val userPreferencesFlow: Flow<UserPreference>
        get() = dataSource.userPreferencesFlow

    override suspend fun updateUser(user: UserPreference) {
        dataSource.updateUser(user)
    }
}