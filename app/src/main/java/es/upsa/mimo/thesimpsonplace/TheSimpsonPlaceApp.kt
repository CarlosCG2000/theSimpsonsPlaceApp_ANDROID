package es.upsa.mimo.thesimpsonplace

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// _______ Instanciando automaticamente las dependencias con Hilt que lo maneje en toda la app _______
@HiltAndroidApp
class TheSimpsonPlaceApp: Application()

// _______ Instanciando manualmente _______
/** Llamo al contexto de la App donde he definido la BD, para pasarlelo al Compose
val Context.database
get() = (applicationContext as TheSimpsonPlaceApp).database
 */

/** class TheSimpsonPlaceApp : Application() {
    @Volatile /** @Volatile: Esta anotación es clave. Asegura que cualquier cambio en la variable database sea visible de inmediato para todos los hilos. Sin volatile, un hilo podría estar usando una versión "cacheada" y desactualizada de la variable database, lo que llevaría a inconsistencias. */
    var database: TheSimpsonsDatabaseRoom? = null // Variable de la BD
        private set // Desde fuera no se puede modificar

    override fun onCreate() {
        super.onCreate()
        initDatabase() // Inicializa la BD
    }

    /** Pasos para la migración en Room
    * 1. Aumenta la versión de la base de datos (de version = 1 a version = 2). En 'TheSimpsonsDatabaseRoom'.
    * 2. Crea la migración Migration(1,2) para añadir las nuevas tablas. Variable 'MIGRATION_1_2', en este fichero.
    * 3. Registra la migración en Room.databaseBuilder. Propiedad 'addMigrations' en este fichero (función 'initDatabase').
    **/
    private val migrationVersion1toVersion2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // Crear la tabla episodes
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

            // Crear la tabla quotes
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

    /** Obtenemos la instancia de la BD de ROOM */
    private fun initDatabase(): TheSimpsonsDatabaseRoom {
        /** Patrón de "bloqueo de doble verificación" (double-checked locking), fundamental para la inicialización segura de singletons en entornos multi-hilo.
        * Primero, verifica si database ya está inicializada. Si no es null, devuelve la instancia existente de inmediato, evitando la sincronización. Esto optimiza el rendimiento.
        * Si database es null, el código entra en un bloque synchronized. Esto garantiza que solo un hilo a la vez pueda ejecutar el código dentro de este bloque, previniendo así las condiciones de carrera durante la creación de la base de datos.
        **/
        return database ?: synchronized(this) {
            /** La instancia de la base de datos se crea dentro del bloque sincronizado. */
            val instance = Room.databaseBuilder(
                this,                           // El contexto de la aplicación
                TheSimpsonsDatabaseRoom::class.java,    // La clase de la base de datos
                "the_simpsons_database"         // Nombre de la base de datos
            )
            //.fallbackToDestructiveMigration()         // ⚠️ Cuidado - Borra y recrea la BD
            .addMigrations(migrationVersion1toVersion2) // Aplicar migración
            .build()

            database = instance /** La nueva instancia se asigna a database */
            instance
        }
    }

    /** ¿¿¿¿¿¿¿¿¿ OJOOOO -> AQUI NO TENDRIA QUE HACER TAMBIEN LA INICIACION DE LA BASE DE DATOS DE DATASTORE --> FIJARSE EN 'MY LIST' ???????? */
 }
*/

// _______ Instanciando manualmente las dependencias con 'Factory' (de personajes y episodios, faltaría la de citas) _______
/**
class TheSimpsonPlaceApp : Application() {

    // Primero obtenemos el context de la aplicación
    private lateinit var appContext: Context

    // La implementacion de Character en producción de Impl.
    private lateinit var characterDao: CharacterDao
    private lateinit var characterDatabaseDao: CharacterDatabaseDao
    private lateinit var characterRepository: CharaterRepository

    lateinit var getAllCharacters: GetAllCharactersUseCase
    lateinit var getCharactersByName: GetFilterNameCharactersUseCase
    lateinit var getAllCharactersDb: FetchAllCharactersDbUseCase
    lateinit var insertCharacterDb: InsertCharacterDbUseCase
    lateinit var deleteCharacterDb: UpdateCharacterDbUseCase

    // La implementacion de Episode en producción de Impl.
    private lateinit var episodeDao: EpisodeDao
    private lateinit var episodeDatabaseDao: EpisodeDatabaseDao
    private lateinit var episodeRepository: EpisodeRepository

    // Casos de uso de la datos obtenido de la fuente principal
    lateinit var getAllEpisodes: GetAllEpisodesUseCase
    lateinit var getEpisodeById: GetEpisodeByIdUseCase
    lateinit var getEpisodesByTitle: GetEpisodesByTitleUseCase
    lateinit var getEpisodesByDate: GetEpisodesByDateUseCase
    lateinit var getEpisodesBySeason: GetEpisodesBySeasonUseCase
    lateinit var getEpisodesByChapter: GetEpisodesByChapterUseCase

    // Casos de uso de la datos de la base de datos
    lateinit var getAllEpisodesDb: GetAllEpisodesDbUseCase
    lateinit var  getEpisodeByIdDb: GetEpisodeByIdDbUseCase
    lateinit var getEpisodeByIdsDb: GetEpisodeByIdsDbUseCase
    lateinit var updateEpisodeDb: UpdateEpisodeDbUseCase
    lateinit var insertEpisodeDb: InsertEpisodeDbUseCase

    override fun onCreate() {
        super.onCreate()

        // Ahora puedes asignar el contexto después de llamar a super.onCreate()
        appContext = applicationContext

        // Luego de inicializar el contexto, puedes proceder con las inicializaciones
        characterDao = CharacterDaoJson(appContext, "personajes_data.json", "imagenes_data.json")
        characterDatabaseDao = CharacterDatabaseDaoRoom()
        characterRepository = CharaterRepositoryImpl(characterDao, characterDatabaseDao)

        getAllCharacters = GetAllCharactersUseCaseImpl(characterRepository)
        getCharactersByName = GetFilterNameCharactersUseCaseImpl(characterRepository)
        getAllCharactersDb = FetchAllCharactersDbUseCaseImpl(characterRepository)
        insertCharacterDb = InsertCharacterDbUseCaseImpl(characterRepository)
        deleteCharacterDb = UpdateCharacterDbUseCaseImpl(characterRepository)

        // Luego de inicializar el contexto, puedes proceder con las inicializaciones
        episodeDao = EpisodeDaoJson(appContext, "episodios_data.json")
        episodeDatabaseDao = EpisodeDatabaseDaoRoom()
        episodeRepository = EpisodeRepositoryImpl(episodeDao, episodeDatabaseDao)

        getAllEpisodes = GetAllEpisodesUseCaseImpl(episodeRepository)
        getEpisodeById = GetEpisodeByIdUseCaseImpl(episodeRepository)
        getEpisodesByTitle = GetEpisodesByTitleUseCaseImpl(episodeRepository)
        getEpisodesByDate = GetEpisodesByDateUseCaseImpl(episodeRepository)
        getEpisodesBySeason = GetEpisodesBySeasonUseCaseImpl(episodeRepository)
        getEpisodesByChapter = GetEpisodesByChapterUseCaseImpl(episodeRepository)

        // Casos de uso de la datos de la base de datos
        getAllEpisodesDb = GetAllEpisodesDbUseCaseImpl(episodeRepository)
        getEpisodeByIdDb = GetEpisodeByIdDbUseCaseImpl(episodeRepository)
        getEpisodeByIdsDb = GetEpisodeByIdsDbUseCaseImpl(episodeRepository)
        updateEpisodeDb = UpdateEpisodeDbUseCaseImpl(episodeRepository)
        insertEpisodeDb = InsertEpisodeDbUseCaseImpl(episodeRepository)

    }
}
*/
