package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.entities.Character
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.DeleteCharacterDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetAllCharactersDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetCharacterDbByIdUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.InsertCharacterDbUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCharactersDBViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersDbUseCase,
    private val getCharacterByIdUseCase: GetCharacterDbByIdUseCase,
    private val insertCharacterUseCase: InsertCharacterDbUseCase,
    private val deleteCharacterUseCase: DeleteCharacterDbUseCase
) : ViewModel() {

    private val _favoriteCharacters = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteCharacters: StateFlow<Set<Int>> = _favoriteCharacters.asStateFlow()

    // Estado reactivo que almacena los personajes favoritos en Room
    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters.asStateFlow()


    init {
        loadFavorites()
    }

    // 🔹 Cargar personajes y marcar favoritos
    private fun loadFavorites() {
        viewModelScope.launch {
            getAllCharactersUseCase.execute().collect { charactersList ->
                _characters.value = charactersList
                _favoriteCharacters.value = charactersList.mapNotNull {
                    if (it.esFavorito) it.id else null
                }.toSet()
            }
        }
    }

    fun toggleFavorite(character: Character) {
        viewModelScope.launch {
            val existsCharacter = getCharacterByIdUseCase.execute(character.id)

            if (existsCharacter == null) {
                insertCharacterUseCase.execute(character)
            } else {
                deleteCharacterUseCase.execute(existsCharacter)
            }
           loadFavorites() // 🔄 Actualiza la lista de favoritos
        }
    }
}