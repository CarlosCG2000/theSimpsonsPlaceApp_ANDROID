package es.upsa.mimo.thesimpsonplace.data.entities.quote

import com.google.gson.annotations.SerializedName

data class QuoteDTO (
    @SerializedName("quote") val cita: String?,     // '@SerializedName' y no '@SerialName'
    @SerializedName("character") val personaje: String?,
    @SerializedName("image") val imagen: String?
)
