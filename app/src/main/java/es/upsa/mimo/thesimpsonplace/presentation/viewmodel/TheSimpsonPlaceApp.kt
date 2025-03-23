package es.upsa.mimo.thesimpsonplace.presentation.viewmodel

import android.app.Application
import android.content.Context
import es.upsa.mimo.thesimpsonplace.data.sources.database.CharacterDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.sources.database.EpisodeDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.sources.database.impl.CharacterDatabaseDaoRoom
import es.upsa.mimo.thesimpsonplace.data.sources.database.impl.EpisodeDatabaseDaoRoom
import es.upsa.mimo.thesimpsonplace.data.sources.service.CharacterDao
import es.upsa.mimo.thesimpsonplace.data.sources.service.EpisodeDao
import es.upsa.mimo.thesimpsonplace.data.sources.service.impl.CharacterDaoJson
import es.upsa.mimo.thesimpsonplace.data.sources.service.impl.EpisodeDaoJson
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import es.upsa.mimo.thesimpsonplace.domain.repository.EpisodeRepository
import es.upsa.mimo.thesimpsonplace.domain.repository.impl.CharaterRepositoryImpl
import es.upsa.mimo.thesimpsonplace.domain.repository.impl.EpisodeRepositoryImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.FetchAllCharactersDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetAllCharactersUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetFilterNameCharactersUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.InsertCharacterDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.UpdateCharacterDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetAllEpisodesDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetAllEpisodesUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodeByIdDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodeByIdUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodeByIdsDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesByChapterUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesByDateUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesBySeasonUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.GetEpisodesByTitleUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.InsertEpisodeDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.UpdateEpisodeDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character.FetchAllCharactersDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character.GetAllCharactersUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character.GetFilterNameCharactersUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character.InsertCharacterDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character.UpdateCharacterDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.GetAllEpisodesDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.GetAllEpisodesUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.GetEpisodeByIdDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.GetEpisodeByIdUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.GetEpisodeByIdsDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.GetEpisodesByChapterUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.GetEpisodesByDateUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.GetEpisodesBySeasonUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.GetEpisodesByTitleUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.InsertEpisodeDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.UpdateEpisodeDbUseCaseImpl

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