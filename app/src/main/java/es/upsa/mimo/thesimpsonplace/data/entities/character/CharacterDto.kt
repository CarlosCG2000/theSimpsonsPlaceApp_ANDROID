package es.upsa.mimo.thesimpsonplace.data.entities.character

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(
    //Mapea los nombres de los atributos del JSON a los de la clase.
    @SerialName("id") val id: String?, // Puede ser String o Int en la API, lo tratamos como Int
    @SerialName("name") val nombre: String?,
    @SerialName("gender") val genero: String?
) {
    fun getIdAsInt(): Int? {
        return id?.toIntOrNull()
    }
}