package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ProfileFormViewModel: ViewModel() {

    var state by mutableStateOf(ProfileEditStateUI())
        private set // no se puede modificar la varaible fuera de aquí

    fun onLoginClick(user: String, pass: String){
        state = when {
            user.contains('@') -> ProfileEditStateUI(error = "User must be a valid name")
            pass.length < 5 -> ProfileEditStateUI(error = "Password must be at least 5 characters")
            else -> ProfileEditStateUI(loggedIn = true)
        }
    }

     fun onLoggedIn() {
        state = ProfileEditStateUI(loggedIn = false)
    }

}