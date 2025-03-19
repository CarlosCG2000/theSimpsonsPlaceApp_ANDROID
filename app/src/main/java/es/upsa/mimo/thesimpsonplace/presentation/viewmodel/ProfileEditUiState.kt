package es.upsa.mimo.thesimpsonplace.presentation.viewmodel


data class ProfileEditUiState(
    val loggedIn: Boolean = false,
    val error: String? = null
)
