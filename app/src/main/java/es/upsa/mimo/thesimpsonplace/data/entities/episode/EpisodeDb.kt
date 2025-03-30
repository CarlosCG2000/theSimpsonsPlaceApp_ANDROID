package es.upsa.mimo.thesimpsonplace.data.entities.episode

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "episodes")
data class EpisodeDb(
    @PrimaryKey(autoGenerate = false) // importante poner clave primaria
    val id: String,
    val titulo: String,
    val temporada: Int,
    val episodio: Int,
    val lanzamiento: Long, // : Date,
    val directores: List<String>,
    val escritores: List<String>,
    val descripcion: String,
    val valoracion: Boolean,
    val invitados: List<String>,
    var esVisto: Boolean,
    var esFavorito: Boolean
)
