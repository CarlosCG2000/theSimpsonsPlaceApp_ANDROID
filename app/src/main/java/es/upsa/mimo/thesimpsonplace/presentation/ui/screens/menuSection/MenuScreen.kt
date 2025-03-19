package es.upsa.mimo.thesimpsonplace.presentation.ui.screens.menuSection

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun MenuScreen( /** 4 navegaciones */
                navigateToCharacters: () -> Unit,
                navigateToEpisodes: () -> Unit,
                navigateToQuotes: () -> Unit,
                onUserProfile: () -> Unit,
           ) {

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    // Contener el desplazamiento del menú (el icono en el 'topBar' a la izquierda de las 3 rallitas)
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet{
                // 3 navegaciones, en el menú a las 3 secciones 'Personajes', 'Episodios', 'Citas'
                ItemMenuComponent(
                    navigateToCharacters,
                    navigateToEpisodes,
                    navigateToQuotes
                )
            }
        },
        drawerState = drawerState // Tipo de estado de desplazamiento del menú
    ) {
        Scaffold(
            topBar = {
                TopBarMenuComponent( // 1 navegación (al perfil del usuario)
                    navigateToProfile = onUserProfile,
                    openMenu = {
                        scope.launch { drawerState.open() }
                    }
                )
            }

        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues),
                contentAlignment = Alignment.Center) {
                Column(modifier = Modifier.fillMaxSize(), // Ocupa toda la pantalla
                    verticalArrangement = Arrangement.Center, // Centra verticalmente dentro de Column
                    horizontalAlignment = Alignment.CenterHorizontally){ // Centra horizontalmente
                    // LOGO SIMPSONS
                    Text("LOGO SIMPSONS", fontSize = 24.sp, fontWeight = Bold)

                    // Spacer(modifier = Modifier.width(200.dp))

                    // IMAGEN DE DONUT SIMPSONS
                    Text("IMAGEN DE DONUT", fontSize = 24.sp, fontWeight = Bold)
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun MenuScreenPreview() {
    MenuScreen({},{}, {}, {})
}
