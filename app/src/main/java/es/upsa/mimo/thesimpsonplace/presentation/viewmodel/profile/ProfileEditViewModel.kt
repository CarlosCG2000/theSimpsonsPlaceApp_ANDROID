package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upsa.mimo.thesimpsonplace.data.entities.user.UserPreference
import es.upsa.mimo.thesimpsonplace.domain.usescases.user.GetUserPreferencesUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.user.UpdateUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileFormViewModel @Inject constructor( val getUserPreferencesUseCase: GetUserPreferencesUseCase,
                                                val updateUserUseCase: UpdateUserUseCase
                                            ): ViewModel() {

    private val _userState: MutableStateFlow<ProfileEditStateUI> = MutableStateFlow(ProfileEditStateUI()) // Asincrono esta en un hilo secundario
    val userState = _userState.asStateFlow()

    init {
        viewModelScope.launch {
            getUserPreferencesUseCase.userPreferencesFlow.collect { user ->
                _userState.update {
                    it.copy(user)
                }
            }
        }
    }

    fun updateSUser(user: UserPreference) {
        viewModelScope.launch {
            updateUserUseCase.execute(user)
        }
    }

    fun onLoginClick(user: String, pass: String){
        _userState.update {
            when {
                !user.contains('@') -> it.copy(error = "User must be a valid name")
                pass.length < 5 -> it.copy(error = "Password must be at least 5 characters")
                else -> it.copy(loggedIn = true)
            }
        }
    }

     fun onLoggedIn() {
         _userState.update {
             it.copy(loggedIn = false)
         }
     }

}