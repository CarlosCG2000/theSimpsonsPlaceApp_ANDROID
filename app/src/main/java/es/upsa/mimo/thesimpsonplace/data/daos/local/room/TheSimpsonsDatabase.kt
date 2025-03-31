package es.upsa.mimo.thesimpsonplace.data.daos.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterEntity
import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeEntity
import es.upsa.mimo.thesimpsonplace.data.entities.quote.QuoteEntity

// DEFINIMOS NUESTRA BASE DE DATOS
@Database(entities = [
                        CharacterEntity::class,
                        EpisodeEntity::class,
                        QuoteEntity::class
                     ],
          version = 2,  // Versión 2 (cambiar de version 1 (solo tabla de Characters a versión 2 con tabla de Episodes y Quotes)
          exportSchema = false) // Ponerlo a false porque sino al compilar va a dar error.
@TypeConverters(TheSimpsonsConverters::class) // TheSimpsonsConverters para cambiar los tipos primitivos de DB a los de la app
abstract class TheSimpsonsDatabaseRoom: RoomDatabase() { // La BD que extiende de RoomDatabase()
    abstract fun characterDbDao(): CharacterDatabaseDao // Las operaciones para la Base de datos de la entidad de Todo
    abstract fun episodeDbDao(): EpisodeDatabaseDao
    abstract fun quoteDbDao(): QuoteDatabaseDao
}

