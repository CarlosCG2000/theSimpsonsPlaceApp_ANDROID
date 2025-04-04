package es.upsa.mimo.thesimpsonplace.data.entities.quote

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URL

@Entity(tableName = QuoteEntity.TABLE_NAME)
data class QuoteEntity(
    @PrimaryKey(autoGenerate = false) // importante poner clave primaria
    val cita: String,
    val personaje: String,
    /**  @SerializedName("image") */ val imagen: URL
){
    companion object {
        const val TABLE_NAME = "quotes"
    }
}


