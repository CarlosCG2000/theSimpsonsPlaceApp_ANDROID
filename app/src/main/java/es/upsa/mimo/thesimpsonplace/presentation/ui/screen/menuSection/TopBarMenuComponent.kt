package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.menuSection

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.tooling.preview.Preview
import es.upsa.mimo.thesimpsonplace.R


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarMenuComponent(openMenu:() -> Unit,
                        navigateToProfile:() -> Unit) {

    CenterAlignedTopAppBar( // Puede ser tambien: TopAppBar, CenterAlignedTopAppBar, MediumTopAppBar, LargeTopAppBar

        title = { Text( text = stringResource(R.string.bienvenido),
                        fontWeight = SemiBold) },

        navigationIcon = { // Icono del menú
            IconButton(onClick = openMenu) {
                Icon(   imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(R.string.icono_del_men_desplegable)
                )
            }
        },

        actions = {
            IconButton(onClick = { navigateToProfile() }) {
                Icon(   imageVector = Icons.Default.Person,
                    contentDescription = stringResource(R.string.icono_del_b_squeda)
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