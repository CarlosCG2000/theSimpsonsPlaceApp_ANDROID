package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character

import android.app.Application
import android.content.Context
import es.upsa.mimo.thesimpsonplace.data.sources.database.CharacterDatabaseDao
import es.upsa.mimo.thesimpsonplace.data.sources.database.impl.CharacterDatabaseDaoRoom
import es.upsa.mimo.thesimpsonplace.data.sources.service.CharacterDao
import es.upsa.mimo.thesimpsonplace.data.sources.service.impl.CharacterDaoJson
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import es.upsa.mimo.thesimpsonplace.domain.repository.impl.CharaterRepositoryImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.FetchAllCharactersDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetAllCharactersUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetFilterNameCharactersUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.InsertCharacterDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.UpdateCharacterDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character.FetchAllCharactersDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character.GetAllCharactersUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character.GetFilterNameCharactersUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character.InsertCharacterDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character.UpdateCharacterDbUseCaseImpl

class ListCharacterApplication: Application() {
    // Primero obtenemos el context de la aplicación
    private lateinit var appContext: Context

    // La implementacion en producción de Impl.
    private lateinit var characterDao: CharacterDao
    private lateinit var characterDatabaseDao: CharacterDatabaseDao
    private lateinit var characterRepository: CharaterRepository

    lateinit var getAllCharacters: GetAllCharactersUseCase
    lateinit var getCharactersByName: GetFilterNameCharactersUseCase
    lateinit var getAllCharactersDb: FetchAllCharactersDbUseCase
    lateinit var insertCharacterDb: InsertCharacterDbUseCase
    lateinit var deleteCharacterDb: UpdateCharacterDbUseCase

    override fun onCreate() {
        super.onCreate()

        // Ahora puedes asignar el contexto después de llamar a super.onCreate()
        appContext = applicationContext

        // Luego de inicializar el contexto, puedes proceder con las inicializaciones
        characterDao = CharacterDaoJson(appContext, "personajes_data.json")
        characterDatabaseDao = CharacterDatabaseDaoRoom()
        characterRepository = CharaterRepositoryImpl(characterDao, characterDatabaseDao)

        getAllCharacters = GetAllCharactersUseCaseImpl(characterRepository)
        getCharactersByName = GetFilterNameCharactersUseCaseImpl(characterRepository)
        getAllCharactersDb = FetchAllCharactersDbUseCaseImpl(characterRepository)
        insertCharacterDb = InsertCharacterDbUseCaseImpl(characterRepository)
        deleteCharacterDb = UpdateCharacterDbUseCaseImpl(characterRepository)
    }
}