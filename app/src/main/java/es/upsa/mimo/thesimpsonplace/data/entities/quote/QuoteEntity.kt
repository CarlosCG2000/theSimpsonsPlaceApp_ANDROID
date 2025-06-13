package es.upsa.mimo.thesimpsonplace.data.entities.quote

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URL

// Tabla de Citas en la BD de ROOM
@Entity(tableName = QuoteEntity.TABLE_NAME)
data class QuoteEntity(
    @PrimaryKey(autoGenerate = false)
    val cita: String,
    val personaje: String,
    /**  @SerializedName("image") */ val imagen: URL
){
    companion object {
        const val TABLE_NAME = "quotes"
    }
}


