package es.upsa.mimo.thesimpsonplace.data.entities.character

import kotlinx.serialization.Serializable

@Serializable
data class ImageDto (
    val name: String?,
    val image: String?
)
