package es.upsa.mimo.thesimpsonplace.data.entities.quote

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URL

@Entity(tableName = "quotes")
data class QuoteEntity(
    @PrimaryKey(autoGenerate = false) // importante poner clave primaria
    val cita: String,
    val personaje: String,
    val imagen: URL
)