package es.upsa.mimo.thesimpsonplace.data.entities.episode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Representa el episodio tal y como se recibe del JSON.
@Serializable
data class EpisodeDTO(
    @SerialName("disneyplus_id")  val id: String?,
    @SerialName("title") val titulo: String?,
    @SerialName("season") val temporada: Int?,
    @SerialName("episode") val episodio: Int?,
    @SerialName("release_date") val lanzamiento: String?,  // Convertirlo a Date? en la extension: toEpisode
    @SerialName("directors") val directores: List<String>?,
    @SerialName("writers") val escritores: List<String>?,
    @SerialName("description") val descripcion: String?,
    @SerialName("good") val valoracion: Boolean?,
    @SerialName("guest_stars") val invitados: List<String>?
)

// ROOM
// 	•	Almacenamos lanzamiento como Long (timestamp) en lugar de Date porque Room no soporta Date directamente.
/**
    @Entity(tableName = "episode")
    data class EpisodeEntity(
    @PrimaryKey val id: String,
    val titulo: String,
    val temporada: Int,
    val episodio: Int,
    val lanzamiento: Long, // Guardamos la fecha como timestamp para Room
    val descripcion: String,
    val esFavorito: Boolean = false,
    val esVisto: Boolean = false
    )
 */