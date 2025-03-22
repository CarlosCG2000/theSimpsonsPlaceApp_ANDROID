package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character

import android.app.Application
import androidx.compose.ui.platform.LocalContext
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

    // La implementacion en producción de Impl.
    private val characterDao: CharacterDao = CharacterDaoJson(applicationContext, "prod/personajes_data.json")
    private val characterDatabaseDao: CharacterDatabaseDao = CharacterDatabaseDaoRoom()
    private val characterRepository: CharaterRepository = CharaterRepositoryImpl(characterDao, characterDatabaseDao)

    val getAllCharacters: GetAllCharactersUseCase = GetAllCharactersUseCaseImpl(characterRepository)
    val getCharactersByName: GetFilterNameCharactersUseCase = GetFilterNameCharactersUseCaseImpl(characterRepository)
    val getAllCharactersDb: FetchAllCharactersDbUseCase = FetchAllCharactersDbUseCaseImpl(characterRepository)
    val insertCharacterDb: InsertCharacterDbUseCase = InsertCharacterDbUseCaseImpl(characterRepository)
    val deleteCharacterDb: UpdateCharacterDbUseCase = UpdateCharacterDbUseCaseImpl(characterRepository)

    override fun onCreate(){
        super.onCreate()
    }
}