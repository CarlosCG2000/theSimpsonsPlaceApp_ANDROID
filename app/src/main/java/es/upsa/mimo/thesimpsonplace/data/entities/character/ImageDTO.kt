package es.upsa.mimo.thesimpsonplace.data.entities.character

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageDTO (
    @SerialName("name") val name: String?,
    @SerialName("image") val image: String?
)
