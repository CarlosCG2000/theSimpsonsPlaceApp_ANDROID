package es.upsa.mimo.thesimpsonplace.data.entities.episode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Contenedor de la lista de episodios del JSON.
@Serializable
data class EpisodesDto(
    @SerialName("episodes") val episodios: List<EpisodeDto>?
)