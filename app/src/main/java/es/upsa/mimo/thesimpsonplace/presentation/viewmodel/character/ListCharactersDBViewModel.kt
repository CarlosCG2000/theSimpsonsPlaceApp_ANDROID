package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upsa.mimo.thesimpsonplace.data.CharacterDatabaseRoomDao
import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDb
import es.upsa.mimo.thesimpsonplace.data.mappers.toCharacter
import es.upsa.mimo.thesimpsonplace.data.mappers.toCharacterDb
import es.upsa.mimo.thesimpsonplace.domain.entities.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCharactersDBViewModel @Inject constructor(
    private val characterDao: CharacterDatabaseRoomDao
) : ViewModel() {

    private val _favoriteCharacters = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteCharacters: StateFlow<Set<Int>> = _favoriteCharacters

    // Estado reactivo que almacena los personajes favoritos en Room
    val characters: StateFlow<List<Character>> =
        characterDao.getAllCharactersDb().map { list -> list.map { it.toCharacter() } }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            characterDao.getAllCharactersDb().collect { characters ->
                _favoriteCharacters.value = characters.map { it.id }.toSet()
            }
        }
    }

    fun toggleFavorite(character: Character) {
        viewModelScope.launch {
            val exists = characterDao.getCharacterById(character.id)
            if (exists == null) {
                characterDao.insertCharacterDb(character.toCharacterDb())
            } else {
                characterDao.deleteCharacterDb(exists)
            }
           // loadFavorites() // 🔄 Actualiza la lista de favoritos
        }
    }
}