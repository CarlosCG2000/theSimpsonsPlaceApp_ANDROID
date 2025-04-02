package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.profile

import es.upsa.mimo.thesimpsonplace.data.entities.user.Language
import es.upsa.mimo.thesimpsonplace.data.entities.user.UserPreference

data class ProfileStateUI(
    val user: UserPreference = UserPreference(), // username, darkMode, language
    val loggedIn: Boolean = false,
    val error: String? = null
)
