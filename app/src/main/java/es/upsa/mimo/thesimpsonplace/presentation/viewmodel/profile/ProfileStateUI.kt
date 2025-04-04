package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.profile

import es.upsa.mimo.thesimpsonplace.data.entities.user.UserPreference
import es.upsa.mimo.thesimpsonplace.domain.models.Character

data class ProfileStateUI(
    val user: UserPreference = UserPreference(), // username, darkMode, language
    val loggedIn: Boolean = false,
    val error: String? = null,
    val topCharacters: List<Pair<Character, Int>> = emptyList(), // Personaje y número de citas favoritas de dicho personaje
    val topSeasons: List<Pair<Int, Int>> = emptyList() // Número de temporada y número de episodios favoritos de dicho temporada
)
