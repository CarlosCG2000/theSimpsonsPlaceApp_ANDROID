package es.upsa.mimo.thesimpsonplace.data.entities.character

import androidx.room.Entity
import androidx.room.PrimaryKey

// Tabla de Personajes en la BD de ROOM
@Entity(tableName = CharacterEntity.TABLE_NAME)
data class CharacterEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    // @ColumnInfo(name = "nombre") // Para que coincida con el JSON
    val nombre: String,
    val genero: Gender,             // Contiene el género como Gender
    val imagen: String? = null
) {
    companion object {
        const val TABLE_NAME = "characters"
    }
}
