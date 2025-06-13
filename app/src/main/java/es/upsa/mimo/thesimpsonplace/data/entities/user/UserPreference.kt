package es.upsa.mimo.thesimpsonplace.data.entities.user

data class UserPreference (
    val username: String = "Anónimo",
    val darkMode: Boolean = true,
    val language: Language = Language.SPANISH
)