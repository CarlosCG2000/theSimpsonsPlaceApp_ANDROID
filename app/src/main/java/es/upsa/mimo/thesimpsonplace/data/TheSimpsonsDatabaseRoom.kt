package es.upsa.mimo.thesimpsonplace.data

import androidx.room.Database
import androidx.room.RoomDatabase
import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDb
import es.upsa.mimo.thesimpsonplace.data.sources.database.impl.CharacterDatabaseRoomDao

// DEFINIMOS NUESTRA BASE DE DATOS
@Database(entities = [CharacterDb::class],  // Solo tenemos una Entity de momemento
          version = 1,  // Versión 1
          exportSchema = false) // Ponerlo a false porque sino al compilar va a dar error.
abstract class TheSimpsonsDatabaseRoom : RoomDatabase() { // La BD que extiende de RoomDatabase()
    abstract fun characterDbDao(): CharacterDatabaseRoomDao // Las operaciones para la Base de datos de la entidad de Todo
}

