package es.upsa.mimo.thesimpsonplace.data.entities.character

import kotlinx.serialization.Serializable

@Serializable
data class ImageDTO (
    val name: String?,
    val image: String?
)
