package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.profile

import es.upsa.mimo.thesimpsonplace.data.entities.user.UserPreference


data class ProfileEditStateUI(
    val user: UserPreference = UserPreference(),
    val loggedIn: Boolean = false,
    val error: String? = null
)
