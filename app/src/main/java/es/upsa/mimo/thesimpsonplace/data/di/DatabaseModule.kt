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
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import javax.inject.Qualifier
import javax.inject.Singleton

/**
    DatabaseModule.kt (Provee DAOs de Room)
    Este módulo se encarga de proporcionar los DAOs de Room y DataStore, permitiendo la interacción con la base de datos.
    Se usa @Singleton solo para EpisodeDatabaseDao y QuoteDatabaseDao, lo cual no es necesario porque Room ya maneja el Singleton a nivel de TheSimpsonsDatabaseRoom.

    ✔ Funciones @Provides
        • provideCharacterDatabaseDao() → Retorna CharacterDatabaseDao.
        • provideEpisodeDatabaseDao() → Retorna EpisodeDatabaseDao.
        • provideQuoteDatabaseDao() → Retorna QuoteDatabaseDao.

    ✔ TheSimpsonsDatabaseRoom para Room.
    ✔ DataStore para GameDataStore y UserDataStore.

    ✔ Provisión de Room Database
        Nota: Esto asegura que Room solo tenga una instancia de la base de datos.
        No se puede usar '@Inject constructor(...)' en los DAO de Room porque Room no lo soporta directamente.
        Si DAO depende de una base de datos (Room), obligatoriamente se usa '@Provides' en 'DatabaseModule' porque Room requiere un 'DatabaseBuilder', y Hilt no puede inferirlo solo con @Inject contructor().

    ✔ DataStore con Qualifiers
        • Se usa @Qualifier para diferenciar los DataStore<Preferences> de “game” y “user”.
        • Permite inyectar dependencias sin que haya conflicto entre ambas instancias.
*/
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    // Creo la instancia de ROOM
    @Singleton
    @Provides
    fun provideDatabaseRoom(@ApplicationContext context: Context): TheSimpsonsDatabaseRoom {
        return Room.databaseBuilder(
            context,
            TheSimpsonsDatabaseRoom::class.java,
            "the_simpsons_database"
        )
        // .fallbackToDestructiveMigration()                         // Cuidado - Borra y recrea la BD
        // .addCallback(YourSimpsonDatabaseCallback(YOUR_APP_SCOPE)) // Necesitarías un CoroutineScope inyectable, para poder recargar con datos por defecto la BD
        .addMigrations(MIGRATION_1_2)                                // Aplica la migración aquí
        .build()
    }

    // Define la migración con las nuevas tablas
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS episodes (
                id TEXT PRIMARY KEY NOT NULL,
                titulo TEXT NOT NULL,
                temporada INTEGER NOT NULL,
                episodio INTEGER NOT NULL,
                lanzamiento INTEGER NOT NULL,
                directores TEXT NOT NULL,
                escritores TEXT NOT NULL,
                descripcion TEXT NOT NULL,
                valoracion INTEGER NOT NULL,
                invitados TEXT NOT NULL,
                esVisto INTEGER NOT NULL,
                esFavorito INTEGER NOT NULL
            )
            """.trimIndent()
            )

            db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS quotes (
                cita TEXT PRIMARY KEY NOT NULL,
                personaje TEXT NOT NULL,
                imagen TEXT NOT NULL
            )
            """.trimIndent()
            )
        }
    }

    // Los providers de DAOs de ROOM
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

    /**
     * Como 'gameDataStore' ya es una propiedad de extensión de 'Context', simplemente la usamos.
     * Hilt sabe qué 'DataStore<Preferences>' se usa gracias a la inyección de dependencias basada en los tipos. Sin embargo, en este caso, ambos métodos devuelven un DataStore<Preferences>, por lo que Hilt no puede diferenciar entre 'gameDataStore' y 'userDataStore' automáticamente. Para solucionar esto, usamos calificadores (@Qualifier).
    */

    //  DataStore necesita acceso a un Context para funcionar.
    //  Usar una propiedad de extensión garantiza que cualquier Context tenga acceso al mismo DataStore.
    private val Context.gameDataStore: DataStore<Preferences> by preferencesDataStore(name = "game")
    private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

    // Definir y aplicar los Qualifiers en los Proveedores (@Provides) (dos '@Qualifier' para identificar cada DataStore)
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class GameDataStore

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class UserDataStore

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