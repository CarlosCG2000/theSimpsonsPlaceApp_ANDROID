package es.upsa.mimo.thesimpsonplace.presentation.ui.component

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.AlertDialogDefaults.titleContentColor
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import es.upsa.mimo.thesimpsonplace.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarComponent(title: String, onNavigationArrowBack:() -> Unit ) {

    CenterAlignedTopAppBar( // Puede ser tambien: CenterAlignedTopAppBar, MediumTopAppBar, LargeTopAppBar

        title = { Text(title) },

        navigationIcon = { // Icono del menú
            IconButton(onClick = onNavigationArrowBack) {
                Icon(   imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = stringResource(R.string.volver_al_menu)
                )
                }
            },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface, // Fondo
            titleContentColor = MaterialTheme.colorScheme.onSecondary, // Texto
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun TopBarComponentPreview() {
    TopBarComponent("Pantalla Extra") {}
}

