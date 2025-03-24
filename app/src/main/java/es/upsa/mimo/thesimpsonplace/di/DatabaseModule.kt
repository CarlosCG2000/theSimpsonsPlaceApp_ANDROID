package es.upsa.mimo.thesimpsonplace.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.upsa.mimo.thesimpsonplace.data.sources.database.CharacterDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.sources.database.EpisodeDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.sources.database.QuoteDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.sources.database.impl.CharacterDatabaseDaoRoom
import es.upsa.mimo.thesimpsonplace.data.sources.database.impl.EpisodeDatabaseDaoRoom
import es.upsa.mimo.thesimpsonplace.data.sources.database.impl.QuoteDatabaseDaoRoom
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
    @Singleton
    fun provideCharacterDatabaseDao(): CharacterDatabaseDao {
        return CharacterDatabaseDaoRoom()
    }

    @Provides
    @Singleton
    fun provideEpisodeDatabaseDao(): EpisodeDatabaseDao {
        return EpisodeDatabaseDaoRoom()
    }

    @Provides
    @Singleton
    fun provideQuoteDatabaseDao(): QuoteDatabaseDao {
        return QuoteDatabaseDaoRoom()
    }


}