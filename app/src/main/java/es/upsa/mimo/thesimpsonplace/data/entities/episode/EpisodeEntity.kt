package es.upsa.mimo.thesimpsonplace.data.entities.episode

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

// Tabla de Episodios en la BD de ROOM
@Entity(tableName = EpisodeEntity.TABLE_NAME)
data class EpisodeEntity (
    @PrimaryKey(autoGenerate = false) // Importante poner clave primaria
    val id: String,
    val titulo: String,
    val temporada: Int,
    val episodio: Int,
    val lanzamiento: Date,          // En la BD se convierte a 'Long' a través de la clase 'Converters'
    val directores: List<String>,   // En la BD  se convierte a 'String' a través de la clase 'Converters'
    val escritores: List<String>,   // En la BD  se convierte a 'String' a través de la clase 'Converters'
    val descripcion: String,
    val valoracion: Boolean,
    val invitados: List<String>,    // En la BD  se convierte a 'String' a través de la clase 'Converters'
    var esVisto: Boolean,
    var esFavorito: Boolean
){
    companion object {
        const val TABLE_NAME = "episodes"
    }
}


