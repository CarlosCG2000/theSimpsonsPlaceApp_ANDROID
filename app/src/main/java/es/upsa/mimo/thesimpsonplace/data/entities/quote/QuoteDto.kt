package es.upsa.mimo.thesimpsonplace.data.entities.quote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuoteDto (
    @SerialName("quote") val cita: String,
    @SerialName("character") val personaje: String,
    @SerialName("image") val imagen: String
)

// 4️⃣ Integración con Room
//Crea la entidad QuoteEntity:
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