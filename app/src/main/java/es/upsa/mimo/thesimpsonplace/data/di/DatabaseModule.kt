package es.upsa.mimo.thesimpsonplace.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.upsa.mimo.thesimpsonplace.data.daos.local.room.TheSimpsonsDatabaseRoom
import es.upsa.mimo.thesimpsonplace.data.daos.local.room.CharacterDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.daos.local.room.EpisodeDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.daos.local.room.QuoteDatabaseDao
import javax.inject.Singleton

// DatabaseModule exclusivo para base de datos Room
// Si tu DAO depende de una base de datos (Room), obligatoriamente se usa '@Provides' en 'DatabaseModule' porque Room requiere un 'DatabaseBuilder', y Hilt no puede inferirlo solo con @Inject contructor(). 🛑 No uses '@Inject constructor(...)' en los DAO de Room porque Room no lo soporta directamente.
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

//    @Provides
//    @Singleton
//    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
//        return Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()
//    }

    @Provides
    fun provideCharacterDao(database: TheSimpsonsDatabaseRoom): CharacterDatabaseDao {
        return database.characterDbDao()
    }

    @Provides
    @Singleton
    fun provideEpisodeDatabaseDao(database: TheSimpsonsDatabaseRoom): EpisodeDatabaseDao {
        return database.episodeDbDao()
    }

    @Provides
    @Singleton
    fun provideQuoteDatabaseDao(database: TheSimpsonsDatabaseRoom): QuoteDatabaseDao {
        return database.quoteDbDao()
    }


}