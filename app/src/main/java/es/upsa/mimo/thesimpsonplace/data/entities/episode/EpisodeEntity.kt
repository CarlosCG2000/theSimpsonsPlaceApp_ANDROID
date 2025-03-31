package es.upsa.mimo.thesimpsonplace.data.entities.episode

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "episodes")
data class EpisodeEntity (
    @PrimaryKey(autoGenerate = false) // Importante poner clave primaria
    val id: String,
    val titulo: String,
    val temporada: Int,
    val episodio: Int,
    val lanzamiento: Date, // En la BD se convierte a 'Long' a través de la clase 'Converters'
    val directores: List<String>, // En la BD  se convierte a 'String' a través de la clase 'Converters'
    val escritores: List<String>, // En la BD  se convierte a 'String' a través de la clase 'Converters'
    val descripcion: String,
    val valoracion: Boolean,
    val invitados: List<String>, // En la BD  se convierte a 'String' a través de la clase 'Converters'
    var esVisto: Boolean,
    var esFavorito: Boolean
)


