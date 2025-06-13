package es.upsa.mimo.thesimpsonplace.data.daos.local.room

import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterEntity
import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeEntity
import es.upsa.mimo.thesimpsonplace.data.entities.quote.QuoteEntity
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.TypeConverters

@Database(entities = [
                        CharacterEntity::class,
                        EpisodeEntity::class,
                        QuoteEntity::class
                     ],
          version = 2,                                      // Versión 2 (cambiar de version 1 (solo tabla de Characters a versión 2 con tabla de Episodes y Quotes)
          exportSchema = false)                             // Ponerlo a false porque sino al compilar va a dar error.
@TypeConverters(TheSimpsonsConverters::class)               // TheSimpsonsConverters para cambiar los tipos primitivos de DB a los de la app
abstract class TheSimpsonsDatabaseRoom: RoomDatabase() {    // La BD que extiende de RoomDatabase()
    abstract fun characterDbDao(): CharacterDatabaseDao     // Las operaciones para la Base de datos de cada entidad
    abstract fun episodeDbDao(): EpisodeDatabaseDao
    abstract fun quoteDbDao(): QuoteDatabaseDao
}

// Al usar Hilt lo defino en el 'DatabaseModule.kt'
/**
    companion object {
        @Volatile /** @Volatile: Esta anotación es clave. Asegura que cualquier cambio en la variable database sea visible de inmediato para todos los hilos. Sin volatile, un hilo podría estar usando una versión "cacheada" y desactualizada de la variable database, lo que llevaría a inconsistencias. */
        var database: TheSimpsonsDatabaseRoom? = null   // Variable de la BD
            private set                                 // Desde fuera no se puede modificar

        /** Obtenemos la instancia de la BD de ROOM */
        fun initDatabase( context: Context, scope: CoroutineScope ): TheSimpsonsDatabaseRoom {
            /** Patrón de "bloqueo de doble verificación" (double-checked locking), fundamental para la inicialización segura de singletons en entornos multi-hilo.
                * Primero, verifica si database ya está inicializada. Si no es null, devuelve la instancia existente de inmediato, evitando la sincronización. Esto optimiza el rendimiento.
                * Si database es null, el código entra en un bloque synchronized. Esto garantiza que solo un hilo a la vez pueda ejecutar el código dentro de este bloque, previniendo así las condiciones de carrera durante la creación de la base de datos.
            **/
            return database ?: synchronized(this) {
                /** La instancia de la base de datos se crea dentro del bloque sincronizado. */
                val instance = Room.databaseBuilder(
                    context.applicationContext,                             // El contexto de la aplicación
                    TheSimpsonsDatabaseRoom::class.java,                    // La clase de la base de datos
                    "the_simpsons_database"                                 // Nombre de la base de datos
                )
                    // .fallbackToDestructiveMigration()                    // ⚠️ Cuidado - Borra y recrea la BD
                    // .addMigrations(migrationVersion1toVersion2)          // Aplicar migración
                    .build()

                database = instance /** La nueva instancia se asigna a database */
                instance
            }
        }
    }

    /**
     * El TheSimpsonsDatabaseCallback es una clase que extiende RoomDatabase.Callback. Su propósito principal es ejecutar código justo después de que la base de datos Room haya sido creada o abierta. Es perfecto para tareas como precargar datos en la base de datos la primera vez que se crea, o realizar tareas de limpieza cuando la base de datos se abre.
     */
    private class TheSimpsonsDatabaseCallback(private val scope : CoroutineScope) : Callback(){
        /**
         * onCreate: este metodo se invoca solo la primera vez que se crea la base de datos. Esto ocurre cuando la aplicación se instala por primera vez o cuando el archivo de la base de datos no existe y Room necesita crearlo desde cero. Es el lugar ideal para precargar datos iniciales.
         */
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            database?.let { database -> // Accede a la instancia del Singleton de la base de datos si no es nula

                /**
                 * CoroutineScope: esto es crucial porque las operaciones de base de datos (como insert, update, delete) son operaciones de E/S (entrada/salida) que deben ejecutarse en un hilo de fondo para no bloquear el hilo principal (UI). El CoroutineScope te permite lanzar corrutinas para estas operaciones.
                 */
                scope.launch { // Inicia una corrutina para realizar operaciones asíncronas
                    val noteDao = database.characterDbDao()
                    // Inserta datos iniciales en la base de datos
                    // noteDao.insertCharacterDb(CharacterEntity(id = 1, name = "Simpsons", ....))
                }
            }
        }
    }
*/
