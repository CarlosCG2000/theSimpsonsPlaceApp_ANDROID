package es.upsa.mimo.thesimpsonplace.presentation.ui.screens.menuSection

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarMenuComponent(openMenu:() -> Unit,
                        navigateToProfile:() -> Unit) {

    CenterAlignedTopAppBar( // Puede ser tambien: TopAppBar, CenterAlignedTopAppBar, MediumTopAppBar, LargeTopAppBar

        title = { Text("BIENVENIDO") },

        navigationIcon = { // Icono del menú
            IconButton(onClick = openMenu) {
                Icon(   imageVector = Icons.Default.Menu,
                    contentDescription = "Icono del menú desplegable")
            }
        },

        actions = {
            IconButton(onClick = { navigateToProfile() }) {
                Icon(   imageVector = Icons.Default.Person,
                    contentDescription = "Icono del búsqueda"
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun TopBarMenuComponentPreview() {
    TopBarMenuComponent( {}, {})
}