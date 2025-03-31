package es.upsa.mimo.thesimpsonplace.data.entities.character

import kotlinx.serialization.Serializable

@Serializable
enum class Gender(val value: String) { // Enum para representar los géneros.
    Male("Male"),
    Female("Female"),
    Undefined("Not specified");

    companion object { // Dentro se encontrarla objetos estáticas
        // Método para transformar "m" -> Male, "f" -> Female, "" -> Undefined.
        fun fromAbbreviation(gender: String?): Gender {
            return when (gender?.lowercase()) {
                "f" -> Female
                "m" -> Male
                else -> Undefined
            }
        }
    }
}