package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.profileSection.profileEdit

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType.Companion.PrimaryEditable
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue

import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.data.entities.user.Language
import es.upsa.mimo.thesimpsonplace.data.entities.user.UserPreference
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.profile.ProfileViewModel
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.TopBarComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditScreen(onLogin: () -> Unit /** Para la navegación a otra vista */,
                      navigationArrowBack:() -> Unit,
                      viewModel: ProfileViewModel = hiltViewModel()
) {
    val userPreference by viewModel.userState.collectAsState()
    var user by rememberSaveable { mutableStateOf("") } // Estado del campo usuario
    var error = userPreference.error != null // Obtener el error en caso de que exista (no sea nulo)

    var expanded by remember { mutableStateOf(false) }
    val languages = listOf("es", "en", "fr")
    val selectedLanguage = userPreference.user.language

    if (userPreference.loggedIn) { // Si el valor 'loggedIn' es true se realizará el login
        viewModel.onLoggedIn() // Si ya esta logeado se vuelve el 'loggedIn' a false y no entre de manera repetida aqui.
        onLogin()
    }

    LaunchedEffect(userPreference.user.username) {
        user = userPreference.user.username
    }

    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Formulario de Usuario",
                onNavigationArrowBack = navigationArrowBack
            )
        }
    )
    { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
                                .padding(paddingValues)
                                .background(MaterialTheme.colorScheme.primary),
            verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(
                value = user, // Usar directamente el valor del ViewModel
                onValueChange = { user = it },
                isError = error, // mostrar en rojo la label dentro del TextField, si hay error
                label = { Text("User") },
                placeholder = { Text("Write your name") },
                trailingIcon = {
                    { user = "" }
                }
            )

            // En caso de que halla error, se mostrara debajo el error dato en el View Model (estamos en una columna)
            userPreference.error?.let { error ->
                Text(error, color = MaterialTheme.colorScheme.error)
            }

            Button(
                // colors = ButtonDefaults(containerColor = MaterialTheme.colorScheme.secondary),
                enabled = (user.isNotEmpty()),
                onClick = {
                    val userNew = UserPreference(username = user,
                                                 darkMode = userPreference.user.darkMode,
                                                 language = userPreference.user.language )

                    viewModel.updateUser(userNew)

                    viewModel.onLoginClick(user)
                }
            ) { Text("Registrar") }

            Row( modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
                verticalAlignment = Alignment.CenterVertically
                ){

            Text(text = "Modo ${if (userPreference.user.darkMode) "Oscuro" else "Claro"} ")

            Switch(
                checked = userPreference.user.darkMode,
                onCheckedChange = {
                    val userNew = UserPreference(username = userPreference.user.username,
                                                 darkMode = !userPreference.user.darkMode,
                                                 language = userPreference.user.language )
                    viewModel.updateUser(userNew)
                })
            }

            ExposedDropdownMenuBox(
                modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {

                OutlinedTextField(
                    value = TextFieldValue(selectedLanguage.code), // Corregido: usar TextFieldValue
                    onValueChange = {},
                    readOnly = true,
                    label = { "Idioma" },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier.menuAnchor(PrimaryEditable, true) // Modificador correcto
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    languages.forEach { languageItem ->
                        DropdownMenuItem(
                            text = { Text(languageItem) },
                            onClick = {
                                val userNew = userPreference.user.copy(language = Language.fromCode(languageItem))
                                viewModel.updateUser(userNew) // Guardar el idioma en el ViewModel
                                expanded = false
                            }
                        )
                    }
                }

            }
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
// @Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Modo Oscuro")
@Composable
fun ProfileEditScreenPreview() {
    ProfileEditScreen(onLogin = {}, navigationArrowBack = {})
}