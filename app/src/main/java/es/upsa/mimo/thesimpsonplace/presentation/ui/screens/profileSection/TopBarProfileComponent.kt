package es.upsa.mimo.thesimpsonplace.presentation.ui.screens.profileSection

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarProfileComponent( userName: String = "Desconocido",
    onNavigationArrowBack:() -> Unit,
                           onNavigationProfileForm:() -> Unit /**Para el el icono del menú*/) {

    TopAppBar( // Puede ser tambien: CenterAlignedTopAppBar, MediumTopAppBar, LargeTopAppBar

        title = { Text(userName) },

        navigationIcon = { // Icono del menú
            IconButton(onClick = onNavigationArrowBack) {
                Icon(   imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Icono de regreso al menú")
            }
        },

        actions = {
            // Text("Edit")
            IconButton(onClick = { onNavigationProfileForm() }) {
                Icon(   imageVector = Icons.Default.Settings,
                        contentDescription = "Icono con tres puntitos"
                )
            }

        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun TopBarProfileComponentPreview() {
    TopBarProfileComponent("Pepito ", {}, {})
}

