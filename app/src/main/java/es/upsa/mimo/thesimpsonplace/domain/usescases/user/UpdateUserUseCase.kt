package es.upsa.mimo.thesimpsonplace.domain.usescases.user

import es.upsa.mimo.thesimpsonplace.data.entities.user.UserPreference

interface UpdateUserUseCase {
    suspend fun execute(user: UserPreference): Unit
}