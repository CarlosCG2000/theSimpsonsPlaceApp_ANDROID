package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetAllCharactersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCharactersViewModel @Inject constructor(val getAllCharactersUseCase: GetAllCharactersUseCase): ViewModel() {
    private val _stateCharacter: MutableStateFlow<ListCharactersStateUI> = MutableStateFlow(ListCharactersStateUI()) // Asincrono esta en un hilo secundario
    val stateCharacter: StateFlow<ListCharactersStateUI> = _stateCharacter.asStateFlow()

    // Hay que llamar a los casos de uso
    // Debería ejecutarse dentro de 'viewModelScope.launch' para evitar bloqueos y manejar la asincronía correctamente:
    fun getAllCharacters(){
        viewModelScope.launch {
            // _stateCharacter.value.isLoading = true ❌ Esto NO actualiza el StateFlow correctamente, porque isLoading es una propiedad mutable dentro de ListCharactersStateUI, pero no estamos emitiendo un nuevo objeto.
            _stateCharacter.update { it.copy(isLoading = true) } // ✅  Activa el spinner

            val charactersList = getAllCharactersUseCase() // ✅ Obtiene los personajes

            // delay(3000) // ✅ prueba del spinnner del 'isLoading' en el Screen

            // it es 'state.value' que es el valor actual de los contactos y lo actualizamos a 'contactsList'
            _stateCharacter.update {
                it.copy(characters = charactersList, isLoading = false)
            }
        }
    }

    /**
    //  Inyección de dependecias manual
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

