package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.user

import es.upsa.mimo.thesimpsonplace.data.entities.user.UserPreference
import es.upsa.mimo.thesimpsonplace.domain.repository.UserRepository
import es.upsa.mimo.thesimpsonplace.domain.usescases.user.UpdateUserUseCase
import javax.inject.Inject

class UpdateUserUseCaseImpl @Inject constructor(val repository: UserRepository): UpdateUserUseCase {
    override suspend operator fun invoke(user: UserPreference) =
        repository.updateUser(user)
}