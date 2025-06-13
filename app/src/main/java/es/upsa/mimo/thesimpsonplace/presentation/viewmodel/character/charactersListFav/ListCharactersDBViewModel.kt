package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersListFav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.models.Character
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.DeleteCharacterDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetAllCharactersDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetCharacterDbByIdUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.InsertCharacterDbUseCase
import es.upsa.mimo.thesimpsonplace.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCharactersDBViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersDbUseCase,
    private val getCharacterByIdUseCase: GetCharacterDbByIdUseCase,
    private val insertCharacterUseCase: InsertCharacterDbUseCase,
    private val deleteCharacterUseCase: DeleteCharacterDbUseCase
) : ViewModel(), Logger {

    private val _stateCharacterFav = MutableStateFlow<ListCharactersDbStateUI>(ListCharactersDbStateUI())
    val stateCharacterFav: StateFlow<ListCharactersDbStateUI> = _stateCharacterFav.asStateFlow()

    init {
        viewModelScope.launch { loadFavorites()  }
    }

    // Cargar personajes y marcar favoritos
    private fun loadFavorites() {
        _stateCharacterFav.update { it.copy(isLoading = true) }

        viewModelScope.launch {

            getAllCharactersUseCase().collect { charactersList ->

                _stateCharacterFav.update {
                    // todos los personajes de la BD que son favoritos (en este caso siempre van a ser todos)
                    it.copy(charactersSet = charactersList.map { it.id }.toSet(),
                            characters = charactersList,
                            isLoading = false) // todos los personajes de la BD
                }

            }
            logInfo( "Cargando el número de personajes favoritos ${_stateCharacterFav.value.charactersSet.size}" )
        }
    }

    fun toggleFavorite(character: Character) {
        viewModelScope.launch {
            val exists = getCharacterByIdUseCase(character.id) != null
            logInfo( "El personajes cambia de estado ya que antes era $exists" )
            if (exists) deleteCharacterUseCase(character) else insertCharacterUseCase(character)
        }
    }

}