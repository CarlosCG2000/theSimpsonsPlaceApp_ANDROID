package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetAllCharactersUseCase
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.TheSimpsonPlaceApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListCharactersViewModel(val getAllCharacters: GetAllCharactersUseCase): ViewModel() {
    private val _stateCharacter: MutableStateFlow<ListCharactersStateUI> = MutableStateFlow(ListCharactersStateUI()) // Asincrono esta en un hilo secundario
    val stateCharacter: StateFlow<ListCharactersStateUI> = _stateCharacter.asStateFlow()

    // Hay que llamar a los casos de uso
    // Debería ejecutarse dentro de 'viewModelScope.launch' para evitar bloqueos y manejar la asincronía correctamente:
    fun getAllCharacters(){
        viewModelScope.launch {
            val charactersList = getAllCharacters.execute() // recibimos la lista de los personajes

            // it es 'state.value' que es el valor actual de los contactos y lo actualizamos a 'contactsList'
            _stateCharacter.update {
                it.copy(charactersList)
            }
        }
    }

    companion object {
        fun factory(): Factory = object : Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                // Accedemos al objeto Aplication con la implementacion (unica que tenemos)
                // A través de extras se puede acceder al Aplication (al igual que se podia al Bundle).
                val application = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TheSimpsonPlaceApp

                // Puedo acceder a través de 'application' al casos de uso que le pasamos por parámetro.
                return ListCharactersViewModel (application.getAllCharacters) as T
            }
        }
    }
}
