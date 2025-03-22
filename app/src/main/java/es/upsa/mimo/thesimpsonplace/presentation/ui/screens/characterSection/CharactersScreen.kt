package es.upsa.mimo.thesimpsonplace.presentation.ui.screens.characterSection

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import es.upsa.mimo.thesimpsonplace.presentation.ui.components.BottomBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.components.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.CharacterViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.CharactersStateUI
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect

@Composable
fun CharactersScreen(   navigateToFilterCharacters: () -> Unit,
                        navigateToFavoriteCharacters: () -> Unit,
                        navigationArrowBack:() -> Unit
) {
    // val context = LocalContext.current
    val viewModel: CharacterViewModel = viewModel(factory = CharacterViewModel.factory())
    val state: State<CharactersStateUI> = viewModel.stateCharacter.collectAsState() // sincrono para manejarlo en la UI

    //Queremos que siempre que se ejecute mi vista queremos que se ejecute el caso de uso de `queryContacts()` del View Model.
    LaunchedEffect(Unit /*Se ejecute el metodo cuando se modifique lo que tengamos aqui (variables), si tenemos 'Unit' se modificar solo una vez */) {
        viewModel.getAllCharacters()
    }

    Scaffold(
        bottomBar = {
            BottomBarComponent(
                1,
                { },
                navigateToFilterCharacters,
                navigateToFavoriteCharacters
            )
        }, topBar = {
            TopBarComponent(
                title = "Listado de Personajes Fav",
                onNavigationArrowBack = navigationArrowBack
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(), // Ocupa toda la pantalla
                // verticalArrangement = Arrangement.Center, // Centra verticalmente dentro de Column
                horizontalAlignment = Alignment.CenterHorizontally
            ) { // Centra horizontalmente
                // LOGO SIMPSONS
                Text("NavegacionPersonajes", fontSize = 24.sp, fontWeight = Bold)

                LazyColumn() {
                    items(items = state.value.characters){ character ->
                        Text(character.nombre)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun CharactersScreenPreview() {
    Column {
        CharactersScreen({},{}, {})
    }
}