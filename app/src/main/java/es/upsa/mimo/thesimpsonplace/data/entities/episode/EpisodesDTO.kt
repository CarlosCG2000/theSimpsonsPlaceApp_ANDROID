package es.upsa.mimo.thesimpsonplace.data.entities.episode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EpisodesDTO(
    @SerialName("episodes") val episodios: List<EpisodeDTO>?
)