package es.upsa.mimo.thesimpsonplace.data.entities.character

import androidx.room.Entity
import androidx.room.PrimaryKey

// 1_NOS CREAMOS LA ENTITY QUE VA A SER NUESTR TABLA DE LA BASE DE DATOS
@Entity(tableName = CharacterEntity.TABLE_NAME)
data class CharacterEntity(
    @PrimaryKey(autoGenerate = false) // importante poner clave primaria
    val id: Int,
    // @ColumnInfo(name = "nombre") // Para que coincida con el JSON
    val nombre: String,
    val genero: Gender, // Contiene el género como Gender
    val imagen: String? = null
) {
    companion object {
        const val TABLE_NAME = "characters"
    }
}
