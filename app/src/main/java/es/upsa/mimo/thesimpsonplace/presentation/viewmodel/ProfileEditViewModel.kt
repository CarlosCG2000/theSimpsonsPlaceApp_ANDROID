package es.upsa.mimo.thesimpsonplace.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ProfileFormViewModel: ViewModel() {

    var state by mutableStateOf(ProfileEditUiState())
        private set // no se puede modificar la varaible fuera de aquí

    fun onLoginClick(user: String, pass: String){
        state = when {
            user.contains('@') -> ProfileEditUiState(error = "User must be a valid name")
            pass.length < 5 -> ProfileEditUiState(error = "Password must be at least 5 characters")
            else -> ProfileEditUiState(loggedIn = true)
        }
    }

     fun onLoggedIn() {
        state = ProfileEditUiState(loggedIn = false)
    }

}