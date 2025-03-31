package es.upsa.mimo.thesimpsonplace.data.entities.quote

import com.google.gson.annotations.SerializedName

data class QuoteDTO (
    @SerializedName("quote") val cita: String?, // Ojo '@SerializedName' y no '@SerialName'
    @SerializedName("character") val personaje: String?,
    @SerializedName("image") val imagen: String?
)

// 4️⃣ Integración con Room
// Crea la entidad QuoteEntity:
/**
    // @Entity(tableName = "quote") crea una tabla en la BD.
    @Entity(tableName = "quote")
    data class QuoteEntity(
        @PrimaryKey val id: UUID = UUID.randomUUID(),
        val cita: String,
        val personaje: String,
        val imagen: String
    )
*/