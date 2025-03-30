package es.upsa.mimo.thesimpsonplace.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDb
import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeDb
import es.upsa.mimo.thesimpsonplace.data.entities.quote.QuoteDb
import es.upsa.mimo.thesimpsonplace.data.sources.database.impl.CharacterDatabaseDaoRoom
import es.upsa.mimo.thesimpsonplace.data.sources.database.impl.EpisodeDatabaseDaoRoom
import es.upsa.mimo.thesimpsonplace.data.sources.database.impl.QuoteDatabaseDaoRoom
import java.util.Date

//class DateConverter {
//    @TypeConverter
//    fun toDate(timestamp: Long?): Date? {
//        return timestamp?.let { Date(it) }
//    }
//    @TypeConverter
//    fun toTimestamp(date: Date?): Long? {
//        return date?.time
//    }
//}

// DEFINIMOS NUESTRA BASE DE DATOS
@Database(entities = [
                        CharacterDb::class,
                        EpisodeDb::class,
                        QuoteDb::class
                     ],
          version = 1,  // Versión 1 (cambiar de veersión si hago migraciones y nuevas features en la BD)
          exportSchema = false) // Ponerlo a false porque sino al compilar va a dar error.
// @TypeConverters(DateConverter::class) (DUDA, comentado arriba, se supone que las entidades de la BD no pueden tener campos como 'Date' sino tienen que ser tipos fundamentales).
abstract class TheSimpsonsDatabaseRoom : RoomDatabase() { // La BD que extiende de RoomDatabase()
    abstract fun characterDbDao(): CharacterDatabaseDaoRoom // Las operaciones para la Base de datos de la entidad de Todo
    abstract fun episodeDbDao(): EpisodeDatabaseDaoRoom
    abstract fun quoteDbDao(): QuoteDatabaseDaoRoom
}

