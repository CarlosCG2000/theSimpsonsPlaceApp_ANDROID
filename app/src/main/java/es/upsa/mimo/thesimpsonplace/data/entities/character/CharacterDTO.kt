package es.upsa.mimo.thesimpsonplace.data.entities.character

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDTO(
    @SerialName("id") val id: Int?, // Puede ser 'String' o 'Int' en la API, lo tratamos como 'Int'
    @SerialName("name") val nombre: String?,
    @SerialName("gender") val genero: String?,
    val imagen: String? = null
) {
    fun getIdAsInt(): Int? = id
}
