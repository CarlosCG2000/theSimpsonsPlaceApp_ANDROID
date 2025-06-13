package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetAllCharactersUseCase
import es.upsa.mimo.thesimpsonplace.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCharactersViewModel @Inject constructor(val getAllCharactersUseCase: GetAllCharactersUseCase): ViewModel(), Logger {

    private val _stateCharacter: MutableStateFlow<ListCharactersStateUI> = MutableStateFlow(ListCharactersStateUI()) // Asincrono esta en un hilo secundario
    val stateCharacter: StateFlow<ListCharactersStateUI> = _stateCharacter.asStateFlow()

    // Debería ejecutarse dentro de 'viewModelScope.launch' para evitar bloqueos y manejar la asincronía correctamente:
    fun getAllCharacters() {
        viewModelScope.launch {
            // Esto NO actualiza el StateFlow correctamente, porque isLoading es una propiedad mutable dentro de ListCharactersStateUI, pero no estamos emitiendo un nuevo objeto.
            // _stateCharacter.value.isLoading = true

            // Emite un nuevo estado desde el inicio, menos errores de actualización de StateFlow y código más limpio y fácil de mantener.
            _stateCharacter.emit(ListCharactersStateUI(isLoading = true))

            val charactersList = getAllCharactersUseCase() // Obtiene los personajes
            // delay(3000) // prueba del spinnner del 'isLoading' en el Screen

            _stateCharacter.emit(ListCharactersStateUI(characters = charactersList,
                                                        isLoading = false))

            logInfo( "Cargando con existo los personajes ${stateCharacter.value.characters.size}" )
        }
    }

    /**
    // ___________ Inyección de dependecias manual ___________
    companion object {
        fun factory(): Factory = object : Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                // Accedemos al objeto Aplication con la implementacion (unica que tenemos)
                // A través de extras se puede acceder al Aplication (al igual que se podia al Bundle).
                val application = extras(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY) as TheSimpsonPlaceApp

                // Puedo acceder a través de 'application' al casos de uso que le pasamos por parámetro.
                return ListCharactersViewModel (application.getAllCharacters) as T
            }
        }
    }
    */
}

