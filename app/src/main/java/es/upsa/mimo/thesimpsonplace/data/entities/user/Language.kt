package es.upsa.mimo.thesimpsonplace.data.entities.user

enum class Language(val code: String) {
    SPANISH("es"),
    ENGLISH("en"),
    FRENCH("fr");

    companion object {
        fun fromCode(code: String): Language {
            return entries.find { it.code == code } ?: SPANISH
        }
    }
}