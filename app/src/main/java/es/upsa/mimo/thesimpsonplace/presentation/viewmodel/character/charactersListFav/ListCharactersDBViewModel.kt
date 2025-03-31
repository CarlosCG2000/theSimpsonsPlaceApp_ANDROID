package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersListFav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.models.Character
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.DeleteCharacterDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetAllCharactersDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetCharacterDbByIdUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.InsertCharacterDbUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCharactersDBViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersDbUseCase,
    private val getCharacterByIdUseCase: GetCharacterDbByIdUseCase,
    private val insertCharacterUseCase: InsertCharacterDbUseCase,
    private val deleteCharacterUseCase: DeleteCharacterDbUseCase
) : ViewModel() {

    private val _stateCharacterFav = MutableStateFlow<ListCharactersDbStateUI>(ListCharactersDbStateUI())
    val stateCharacterFav: StateFlow<ListCharactersDbStateUI> = _stateCharacterFav.asStateFlow()

//    private val _charactersSet = MutableStateFlow<Set<Int>>(emptySet())
//    val charactersSet: StateFlow<Set<Int>> = _charactersSet.asStateFlow()
//
//    // Estado reactivo que almacena los personajes favoritos en Room
//    private val _characters = MutableStateFlow<List<Character>>(emptyList())
//    val characters: StateFlow<List<Character>> = _characters.asStateFlow()

    init {
        loadFavorites()
    }

    // 🔹 Cargar personajes y marcar favoritos
    private fun loadFavorites() {
        _stateCharacterFav.update { it.copy(isLoading = true) }

        viewModelScope.launch {

            getAllCharactersUseCase.execute().collect { charactersList ->

                _stateCharacterFav.update {
                    it.copy(charactersSet = charactersList.mapNotNull { it.id }.toSet(),  // todos los personajes de la BD que son favoritos (en este caso siempre van a ser todos)
                            characters = charactersList,
                            isLoading = false) // todos los personajes de la BD
                }

            }
        }
    }

    fun toggleFavorite(character: Character) {
        viewModelScope.launch {
            val existsCharacter = getCharacterByIdUseCase.execute(character.id) // se comprueba si existe el personaje en la BD

            if (existsCharacter == null) {
                insertCharacterUseCase.execute(character)
            } else {
                deleteCharacterUseCase.execute(existsCharacter)
            }

           loadFavorites() // 🔄 Actualiza la lista de favoritos
        }
    }
}