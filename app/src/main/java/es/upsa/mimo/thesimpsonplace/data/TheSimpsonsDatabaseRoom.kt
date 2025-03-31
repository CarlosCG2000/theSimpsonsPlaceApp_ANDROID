package es.upsa.mimo.thesimpsonplace.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDb
import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeDb
import es.upsa.mimo.thesimpsonplace.data.entities.quote.QuoteDb
import es.upsa.mimo.thesimpsonplace.data.sources.local.impl.CharacterDatabaseDaoRoom
import es.upsa.mimo.thesimpsonplace.data.sources.local.impl.EpisodeDatabaseDaoRoom
import es.upsa.mimo.thesimpsonplace.data.sources.local.impl.QuoteDatabaseDaoRoom
import java.net.URL
import java.util.Date

class Converters {
    private val gson = Gson()

    // 🔥 Convertir `Date` a `Long` y viceversa
    @TypeConverter
    fun fromDate(date: Date): Long = date.time

    @TypeConverter
    fun toDate(timestamp: Long): Date = timestamp.let { Date(it) }

    // 🔥 Convertir `List<String>` a `String` (JSON) y viceversa
    @TypeConverter
    fun fromStringList(list: List<String>): String = gson.toJson(list)

    @TypeConverter
    fun toStringList(data: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, listType) ?: emptyList()
    }

    // 🔥 Convertir `URL` a `String` y viceversa
    @TypeConverter
    fun fromUrl(url: URL): String = url.toString()

    @TypeConverter
    fun toUrl(urlString: String): URL = URL(urlString)
}

// DEFINIMOS NUESTRA BASE DE DATOS
@Database(entities = [
                        CharacterDb::class,
                        EpisodeDb::class,
                        QuoteDb::class
                     ],
          version = 2,  // Versión 2 (cambiar de version 1 (solo tabla de Characters a versión 2 con tabla de Episodes y Quotes)
          exportSchema = false) // Ponerlo a false porque sino al compilar va a dar error.
@TypeConverters(Converters::class)
abstract class TheSimpsonsDatabaseRoom: RoomDatabase() { // La BD que extiende de RoomDatabase()
    abstract fun characterDbDao(): CharacterDatabaseDaoRoom // Las operaciones para la Base de datos de la entidad de Todo
    abstract fun episodeDbDao(): EpisodeDatabaseDaoRoom
    abstract fun quoteDbDao(): QuoteDatabaseDaoRoom
}

