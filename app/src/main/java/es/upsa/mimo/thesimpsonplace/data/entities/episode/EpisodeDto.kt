package es.upsa.mimo.thesimpsonplace.data.entities.episode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Representa el episodio tal y como se recibe del JSON.
@Serializable
data class EpisodeDto(
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
