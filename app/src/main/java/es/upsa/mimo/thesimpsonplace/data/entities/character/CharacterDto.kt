package es.upsa.mimo.thesimpsonplace.data.entities.character

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(
    //Mapea los nombres de los atributos del JSON a los de la clase.
    @SerialName("id") val id: Int?, // Puede ser String o Int en la API, lo tratamos como Int
    @SerialName("name") val nombre: String?,
    @SerialName("gender") val genero: String?,
    val imagen: String? = null
) {
    fun getIdAsInt(): Int? {
        return id?.toInt()
    }
}

// 1️⃣ ROOM: Entidad CharacterEntity
/**
@Entity(tableName = "character")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val nombre: String,
    val imagen: String,
    val esFavorito: Boolean = false // Indica si el personaje es favorito
)
*/