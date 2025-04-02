package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.menuSection

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.upsa.mimo.thesimpsonplace.R
import kotlinx.coroutines.launch

@Composable
fun MenuScreen(  /** 4 navegaciones */
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
                ItemMenuComponent(        // Mi componente
                    navigateToCharacters, // -> navegación a la sección de personajes
                    navigateToEpisodes,   // -> navegación a la sección de personajes
                    navigateToQuotes      // -> navegación a la sección de personajes
                )
            }
        },
        drawerState = drawerState // Tipo de estado de desplazamiento del menú
    ) {
        Scaffold(
            topBar = {
                TopBarMenuComponent(
                    navigateToProfile = onUserProfile, // 1 navegación (al perfil del usuario)
                    openMenu = {
                        scope.launch { drawerState.open() } // Abir el menú desplegable
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
                    Image(
                        painter = painterResource(id = R.drawable.menu_logo), // 🔹 Reemplaza con tu imagen
                        contentDescription = stringResource(R.string.logo_de_los_simpsons),
                        modifier = Modifier
                            .size(250.dp) // Ajusta el tamaño
                    )

                    Spacer(modifier = Modifier.height(36.dp)) // Espaciado entre imágenes

                    // IMAGEN DE DONUT SIMPSONS
                    Image(
                        painter = painterResource(id = R.drawable.menu_donut), // 🔹 Reemplaza con tu imagen
                        contentDescription = stringResource(R.string.donut_de_los_simpsons),
                        modifier = Modifier
                            .size(300.dp) // Ajusta el tamaño
                    )

                    Spacer(modifier = Modifier.height(100.dp)) // Espaciado entre imágenes
                }

            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun MenuScreenPreview() {
    MenuScreen( {},{}, {}, {})
}
