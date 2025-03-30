package es.upsa.mimo.thesimpsonplace.presentation.viewmodel

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.hilt.android.HiltAndroidApp
import es.upsa.mimo.thesimpsonplace.data.TheSimpsonsDatabaseRoom

val Context.database
    get() = (applicationContext as TheSimpsonPlaceApp).database // llamamos al contexto de la App donde hemos definido la BD, para pasarlelo al compose

// _____________ Instanciando automaticamente las dependencias con Hilt que lo maneje en toda la app. _____________
@HiltAndroidApp
class TheSimpsonPlaceApp : Application(){
    lateinit var database: TheSimpsonsDatabaseRoom // CREAMOS LA DEFINICION DE LA BASE DE DATOS
        private set // DECIMOS QUE DESDE FUERA SOLO SE PUEDA LEER

    override fun onCreate() {
        super.onCreate()

        initDatabase() // INICIALIZAMOS LA CREACION DE NUESTRA BASE DE DATOS
    }

//    ✨ Pasos para la migración en Room
//    1. Aumenta la versión de la base de datos (de version = 1 a version = 2). En 'TheSimpsonsDatabaseRoom'.
//    2. Crea la migración Migration(1,2) para añadir las nuevas tablas. Variable 'MIGRATION_1_2', en este fichero.
//    3. Registra la migración en Room.databaseBuilder.  Propiedad 'addMigrations' en este fichero (función 'initDatabase').
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // 🔥 Crear la tabla episodes
            database.execSQL(
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

            // 🔥 Crear la tabla quotes
            database.execSQL(
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

    // CREAMOS NUESTRA BASE DE DATOS
    private fun initDatabase() {
        database = Room.databaseBuilder(
            this, // EL CONTEXTO DE LA APP
            TheSimpsonsDatabaseRoom::class.java,  // LA BASE DE DATOS QUE QUEREMOS CREAR
            "TheSimpsonsDatabaseRoom" // NOMBRE QUE QUERAMOS DARLE
        )
        //.fallbackToDestructiveMigration() // 🔥 Borra y recrea la BD
        .addMigrations(MIGRATION_1_2) // Aplicar migración
        .build()
    }


}


// _____________ Instanciando manualmente las dependencias (de personajes y episodios, falta la de citas con 'Factory').  _____________
//class TheSimpsonPlaceApp : Application() {
//
//    // Primero obtenemos el context de la aplicación
//    private lateinit var appContext: Context
//
//    // La implementacion de Character en producción de Impl.
//    private lateinit var characterDao: CharacterDao
//    private lateinit var characterDatabaseDao: CharacterDatabaseDao
//    private lateinit var characterRepository: CharaterRepository
//
//    lateinit var getAllCharacters: GetAllCharactersUseCase
//    lateinit var getCharactersByName: GetFilterNameCharactersUseCase
//    lateinit var getAllCharactersDb: FetchAllCharactersDbUseCase
//    lateinit var insertCharacterDb: InsertCharacterDbUseCase
//    lateinit var deleteCharacterDb: UpdateCharacterDbUseCase
//
//    // La implementacion de Episode en producción de Impl.
//    private lateinit var episodeDao: EpisodeDao
//    private lateinit var episodeDatabaseDao: EpisodeDatabaseDao
//    private lateinit var episodeRepository: EpisodeRepository
//
//    // Casos de uso de la datos obtenido de la fuente principal
//    lateinit var getAllEpisodes: GetAllEpisodesUseCase
//    lateinit var getEpisodeById: GetEpisodeByIdUseCase
//    lateinit var getEpisodesByTitle: GetEpisodesByTitleUseCase
//    lateinit var getEpisodesByDate: GetEpisodesByDateUseCase
//    lateinit var getEpisodesBySeason: GetEpisodesBySeasonUseCase
//    lateinit var getEpisodesByChapter: GetEpisodesByChapterUseCase
//
//    // Casos de uso de la datos de la base de datos
//    lateinit var getAllEpisodesDb: GetAllEpisodesDbUseCase
//    lateinit var  getEpisodeByIdDb: GetEpisodeByIdDbUseCase
//    lateinit var getEpisodeByIdsDb: GetEpisodeByIdsDbUseCase
//    lateinit var updateEpisodeDb: UpdateEpisodeDbUseCase
//    lateinit var insertEpisodeDb: InsertEpisodeDbUseCase
//
//    override fun onCreate() {
//        super.onCreate()
//
//        // Ahora puedes asignar el contexto después de llamar a super.onCreate()
//        appContext = applicationContext
//
//        // Luego de inicializar el contexto, puedes proceder con las inicializaciones
//        characterDao = CharacterDaoJson(appContext, "personajes_data.json", "imagenes_data.json")
//        characterDatabaseDao = CharacterDatabaseDaoRoom()
//        characterRepository = CharaterRepositoryImpl(characterDao, characterDatabaseDao)
//
//        getAllCharacters = GetAllCharactersUseCaseImpl(characterRepository)
//        getCharactersByName = GetFilterNameCharactersUseCaseImpl(characterRepository)
//        getAllCharactersDb = FetchAllCharactersDbUseCaseImpl(characterRepository)
//        insertCharacterDb = InsertCharacterDbUseCaseImpl(characterRepository)
//        deleteCharacterDb = UpdateCharacterDbUseCaseImpl(characterRepository)
//
//        // Luego de inicializar el contexto, puedes proceder con las inicializaciones
//        episodeDao = EpisodeDaoJson(appContext, "episodios_data.json")
//        episodeDatabaseDao = EpisodeDatabaseDaoRoom()
//        episodeRepository = EpisodeRepositoryImpl(episodeDao, episodeDatabaseDao)
//
//        getAllEpisodes = GetAllEpisodesUseCaseImpl(episodeRepository)
//        getEpisodeById = GetEpisodeByIdUseCaseImpl(episodeRepository)
//        getEpisodesByTitle = GetEpisodesByTitleUseCaseImpl(episodeRepository)
//        getEpisodesByDate = GetEpisodesByDateUseCaseImpl(episodeRepository)
//        getEpisodesBySeason = GetEpisodesBySeasonUseCaseImpl(episodeRepository)
//        getEpisodesByChapter = GetEpisodesByChapterUseCaseImpl(episodeRepository)
//
//        // Casos de uso de la datos de la base de datos
//        getAllEpisodesDb = GetAllEpisodesDbUseCaseImpl(episodeRepository)
//        getEpisodeByIdDb = GetEpisodeByIdDbUseCaseImpl(episodeRepository)
//        getEpisodeByIdsDb = GetEpisodeByIdsDbUseCaseImpl(episodeRepository)
//        updateEpisodeDb = UpdateEpisodeDbUseCaseImpl(episodeRepository)
//        insertEpisodeDb = InsertEpisodeDbUseCaseImpl(episodeRepository)
//
//    }
//}