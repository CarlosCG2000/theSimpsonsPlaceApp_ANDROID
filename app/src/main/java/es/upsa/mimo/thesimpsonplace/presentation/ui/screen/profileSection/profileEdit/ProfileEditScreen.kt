package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.profileSection.profileEdit

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.input.TextFieldValue

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.R
import es.upsa.mimo.thesimpsonplace.data.entities.user.Language
import es.upsa.mimo.thesimpsonplace.data.entities.user.UserPreference
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.ModifierContainer
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
                title = stringResource(R.string.formulario_de_usuario),
                onNavigationArrowBack = navigationArrowBack
            )
        }
    )
    { paddingValues ->
        Column (ModifierContainer(paddingValues),
        verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally) {

            Column ( modifier = Modifier.clip(RoundedCornerShape(12.dp))
                                        .background(MaterialTheme.colorScheme.secondary)
                                        .padding(50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TextField(
                    value = user, // Usar directamente el valor del ViewModel
                    onValueChange = { user = it },
                    isError = error, // mostrar en rojo la label dentro del TextField, si hay error
                    label = { Text(stringResource(R.string.user)) },
                    placeholder = { Text(stringResource(R.string.write_your_name)) },
                    trailingIcon = {
                        if (user.isNotEmpty()) { // Solo muestra el icono si hay texto
                            IconButton(onClick = { user = "" }) {
                                androidx.compose.material3.Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = stringResource(R.string.limpiar_nombre_del_usuario),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(5.dp)) // Ajusta la altura según sea necesario

                // En caso de que halla error, se mostrara debajo el error dato en el View Model (estamos en una columna)
                userPreference.error?.let { error ->
                    Text(error, color = MaterialTheme.colorScheme.error)
                }

                Spacer(modifier = Modifier.height(16.dp)) // Ajusta la altura según sea necesario

                Button(
                    // colors = ButtonDefaults(containerColor = MaterialTheme.colorScheme.secondary),
                    enabled = (user.isNotEmpty()),
                    onClick = {
                        val userNew = UserPreference(
                            username = user,
                            darkMode = userPreference.user.darkMode,
                            language = userPreference.user.language
                        )

                        viewModel.updateUser(userNew)

                        viewModel.onLoginClick(user)
                    }
                ) { Text(stringResource(R.string.registrar)) }

                Spacer(modifier = Modifier.height(16.dp)) // Ajusta la altura según sea necesario

                Row(
                    modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(text = stringResource(
                        R.string.modo,
                        if (userPreference.user.darkMode) stringResource(R.string.oscuro)
                                    else stringResource(R.string.claro)
                    ),  fontWeight = SemiBold, color = MaterialTheme.colorScheme.onSecondary)

                    Spacer(modifier = Modifier.width(12.dp))

                    Switch(
                        checked = userPreference.user.darkMode,
                        onCheckedChange = {
                            val userNew = UserPreference(
                                username = userPreference.user.username,
                                darkMode = !userPreference.user.darkMode,
                                language = userPreference.user.language
                            )
                            viewModel.updateUser(userNew)
                        })
                }

                Spacer(modifier = Modifier.height(16.dp)) // Ajusta la altura según sea necesario

                Text(text = if (selectedLanguage == Language.SPANISH) stringResource(R.string.idioma_espa_ol)
                            else if (selectedLanguage == Language.ENGLISH) stringResource(R.string.idioma_ingl_s)
                            else stringResource(R.string.idioma_franc_s),
                    fontWeight = SemiBold,
                    color = MaterialTheme.colorScheme.onSecondary)

                ExposedDropdownMenuBox(
                    modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {

                    OutlinedTextField(
                        value = TextFieldValue(selectedLanguage.code), // Corregido: usar TextFieldValue
                        onValueChange = {},
                        readOnly = true,
                        label = { stringResource(R.string.idioma) },
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
                                    val userNew = userPreference.user.copy(
                                        language = Language.fromCode(languageItem)
                                    )
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
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
// @Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Modo Oscuro")
@Composable
fun ProfileEditScreenPreview() {
    ProfileEditScreen(onLogin = {}, navigationArrowBack = {})
}