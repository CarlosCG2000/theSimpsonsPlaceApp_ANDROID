package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upsa.mimo.thesimpsonplace.data.entities.user.UserPreference
import es.upsa.mimo.thesimpsonplace.domain.models.Character
import es.upsa.mimo.thesimpsonplace.domain.models.Episode
import es.upsa.mimo.thesimpsonplace.domain.models.Quote
import es.upsa.mimo.thesimpsonplace.domain.usescases.user.GetUserPreferencesUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.user.UpdateUserUseCase
import es.upsa.mimo.thesimpsonplace.utils.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(val getUserPreferencesUseCase: GetUserPreferencesUseCase,
                                           val updateUserUseCase: UpdateUserUseCase
                                            ): ViewModel(), Logger {

    private val _userState: MutableStateFlow<ProfileStateUI> = MutableStateFlow(ProfileStateUI()) // Asincrono esta en un hilo secundario
    val userState = _userState.asStateFlow()

    init {
        viewModelScope.launch {
            getUserPreferencesUseCase.userPreferencesFlow.collect { user ->
                _userState.update {
                    it.copy(user = user)
                }
            }
            logDebug( "Cargando preferencias de usuario ${userState.value.user.username} ${userState.value.user.language}")
        }
    }

    fun updateUser(user: UserPreference) {
        viewModelScope.launch {
            updateUserUseCase(user)

            _userState.update {
                it.copy(user = user) // ✅ Actualiza el estado en la UI
            }
            logDebug( "Actualizando preferencias de usuario ${userState.value.user.username} ${userState.value.user.language}")
        }
    }

    fun onLoginClick(user: String) {
        _userState.update {

            if (user.isNotEmpty() && user.length > 3) {
                it.copy(loggedIn = true, error = null)
            } else {
                it.copy(error = "Mínino 3 caracteres para el usuario")
            }

//            when {
//                !user.contains('@') -> it.copy(error = "User must be a valid name")
//                pass.length < 5 -> it.copy(error = "Password must be at least 5 characters")
//                else -> it.copy(loggedIn = true)
//            }

        }

        logDebug( "Iniciando sesión con el usuario $user")
    }

     fun onLoggedIn() {
         _userState.update {
             it.copy(loggedIn = false)
         }
            logDebug( "Cerrando sesión del usuario ${userState.value.user.username}")
     }

    fun calculateTopCharactersAndSeasons(
        favoriteCharacters: List<Character>,
        favoriteQuotes: List<Quote>,
        favoriteEpisodes: List<Episode>
    ) {
        viewModelScope.launch {
            val quoteCounts = favoriteQuotes
                .groupingBy { it.personaje } // Agrupar por personaje
                .eachCount() // Contar ocurrencias

            // Paso 2: Filtrar solo los personajes favoritos y mapear con el conteo de citas
            val topCharacters = favoriteCharacters
                .map { character -> character to (quoteCounts[character.nombre] ?: 0) }
                .sortedByDescending { it.second }  // Obtener cuántas citas tiene
                .take(3) // Tomar los 3 primeros
                // .map { it.first } // Obtener solo el personaje y no el numero de citas favoritas

            // Obtener las 3 temporadas con mas episodios favoritos.
            val topSeasons = favoriteEpisodes
                .groupingBy { it.temporada } // 🔹 Agrupar por temporada
                .eachCount() // 🔹 Contar cuántos episodios favoritos tiene cada temporada
                .entries
                .sortedByDescending { it.value }// 🔹 Ordenar por cantidad de episodios favoritos
                .take(3) // 🔹 Tomar las 3 temporadas con más episodios favoritos
                .map { it.key to it.value } // 🔹 Obtener el nombre de la temporada y el número de episodios favoritos

            _userState.update { it.copy(topCharacters = topCharacters, topSeasons = topSeasons) }

            logDebug( "Calculando los personajes y temporadas favoritos ${userState.value.user.username} ${userState.value.user.language}")
        }
    }

}