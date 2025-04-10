package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersFilterName

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetFilterNameCharactersUseCase
import es.upsa.mimo.thesimpsonplace.utils.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCharactersFilterViewModel @Inject constructor(val getFilterNameCharactersUseCase: GetFilterNameCharactersUseCase): ViewModel(), Logger {
    private val _stateCharacterFilter: MutableStateFlow<ListCharactersFilterStateUI> = MutableStateFlow(ListCharactersFilterStateUI()) // Asincrono esta en un hilo secundario
    val stateCharacterFilter: StateFlow<ListCharactersFilterStateUI> = _stateCharacterFilter.asStateFlow()

    // Hay que llamar a los casos de uso
    // Debería ejecutarse dentro de 'viewModelScope.launch' para evitar bloqueos y manejar la asincronía correctamente:
    fun getFilterNameCharacters(name: String) {
        viewModelScope.launch {

            _stateCharacterFilter.update { it.copy(isLoading = true) } // Activa el spinner

            val charactersFilter = getFilterNameCharactersUseCase(name) // Obtiene los personajes filtrados

            // it es 'state.value' que es el valor actual de los contactos y lo actualizamos a 'contactsList'
            _stateCharacterFilter.update {
                it.copy(characters = charactersFilter, isLoading = false)
            }

            logInfo( "Cargando el número de personajes ${_stateCharacterFilter.value.characters.size} por nombre $name" )
        }
    }
}