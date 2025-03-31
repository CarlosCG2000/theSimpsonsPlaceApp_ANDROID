package es.upsa.mimo.thesimpsonplace.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.upsa.mimo.thesimpsonplace.data.daos.local.room.TheSimpsonsDatabaseRoom
import es.upsa.mimo.thesimpsonplace.data.daos.local.room.CharacterDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.daos.local.room.EpisodeDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.daos.local.room.QuoteDatabaseDao
import androidx.datastore.preferences.core.Preferences
import javax.inject.Qualifier
import javax.inject.Singleton

// DatabaseModule exclusivo para base de datos Room
// Si tu DAO depende de una base de datos (Room), obligatoriamente se usa '@Provides' en 'DatabaseModule' porque Room requiere un 'DatabaseBuilder', y Hilt no puede inferirlo solo con @Inject contructor(). 🛑 No uses '@Inject constructor(...)' en los DAO de Room porque Room no lo soporta directamente.
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideCharacterDatabaseDao(database: TheSimpsonsDatabaseRoom): CharacterDatabaseDao {
        return database.characterDbDao()
    }

    @Provides
    fun provideEpisodeDatabaseDao(database: TheSimpsonsDatabaseRoom): EpisodeDatabaseDao {
        return database.episodeDbDao()
    }

    @Provides
    fun provideQuoteDatabaseDao(database: TheSimpsonsDatabaseRoom): QuoteDatabaseDao {
        return database.quoteDbDao()
    }

    // __________ ROOM ____________
    @Singleton
    @Provides
    fun provideDatabaseRoom(@ApplicationContext context: Context): TheSimpsonsDatabaseRoom {
        return Room.databaseBuilder(
            context,
            TheSimpsonsDatabaseRoom::class.java,
            "the_simpsons_database"
        ).build()
    }

//    @Provides
//    @Singleton
//    fun provideGamePreferencesDao(gameDataStore: DataStore<Preferences>): GameDatastoreDaoImpl {
//        return GameDatastoreDaoImpl(gameDataStore)
//    }

//    Le dice a Hilt cómo proveer el DataStore<Preferences> cuando sea necesario en otra clase.
//    Como gameDataStore ya es una propiedad de extensión de Context, simplemente la usamos.
//    Hilt sabe qué DataStore<Preferences> usar gracias a la inyección de dependencias basada en los tipos.
//    Sin embargo, en este caso, ambos métodos devuelven un DataStore<Preferences>, por lo que Hilt no puede diferenciar entre gameDataStore y userDataStore automáticamente. Para solucionar esto, usamos calificadores (@Qualifier).

//  1️⃣ Definir los Qualifiers
//  Crea dos @Qualifier para identificar cada DataStore:
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class GameDataStore

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class UserDataStore

//  DataStore necesita acceso a un Context para funcionar.
//  Usar una propiedad de extensión garantiza que cualquier Context tenga acceso al mismo DataStore.
//  Le pasamos este DataStore por defecto a 'provideGamePreferencesDao'
    private val Context.gameDataStore: DataStore<Preferences> by preferencesDataStore(name = "game")
    private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

//  2️⃣ Aplicar los @Qualifier en los Proveedores (@Provides)
//  Ahora, en AppModule.kt, usa estas anotaciones para que Hilt sepa cuál es cuál:
    @Provides
    @Singleton
    @GameDataStore
    fun provideGameDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.gameDataStore

    @Provides
    @Singleton
    @UserDataStore
    fun provideUserDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.userDataStore
}

/**
    DatabaseModule.kt (Provee DAOs de Room)
    Este módulo se encarga de proporcionar los DAOs de Room, permitiendo la interacción con la base de datos. Y de Room y la DataStore.

    ✔ Funciones @Provides
    • provideCharacterDatabaseDao() → Retorna CharacterDatabaseDao.
    • provideEpisodeDatabaseDao() → Retorna EpisodeDatabaseDao.
    • provideQuoteDatabaseDao() → Retorna QuoteDatabaseDao.

    ✔ TheSimpsonsDatabaseRoom para Room.
    ✔ DataStore para GameDataStore y UserDataStore.

    🔥 Provisión de Room Database
    Nota: Esto asegura que Room solo tenga una instancia de la base de datos.

    🔥 DataStore con Qualifiers
    •	Se usa @Qualifier para diferenciar los DataStore<Preferences> de “game” y “user”.
    •	Permite inyectar dependencias sin que haya conflicto entre ambas instancias.
 */

/**
Nota: Se usa @Singleton solo para EpisodeDatabaseDao y QuoteDatabaseDao, lo cual no es necesario porque Room ya maneja el Singleton a nivel de TheSimpsonsDatabaseRoom.
 */
