package es.upsa.mimo.thesimpsonplace.presentation.ui.screens.characterSection

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersList.ListCharactersViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersList.ListCharactersStateUI
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.entities.Character

@Composable
fun CharactersScreen(
    viewModel: ListCharactersViewModel = hiltViewModel(), // = viewModel(factory = ListCharactersViewModel.factory()
    navigateToFilterCharacters: () -> Unit,
    navigateToFavoriteCharacters: () -> Unit,
    navigationArrowBack:() -> Unit
) {

    val state: State<ListCharactersStateUI> = viewModel.stateCharacter.collectAsState() // sincrono para manejarlo en la UI

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
        },
        topBar = {
            TopBarComponent(
                title = "Listado de Personajes Fav",
                onNavigationArrowBack = navigationArrowBack
            )
        }
    ) { paddingValues ->
        Box(
            contentAlignment = Alignment.Center, // ✅ Asegura que el spinner esté centrado
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Gray) // ✅ Fondo blanco para mejor visibilidad,
        ) {
            if(state.value.isLoading){
                CircularProgressIndicator(
                    color = Color.Yellow // ✅ Cambia el color del spinner a amarillo
                )
            } else {
                CharacterList(paddingValues, state.value.characters)
            }
        }
    }
}

@Composable
fun CharacterList(paddingValues: PaddingValues, character: List<Character>) {
    Column(
        modifier = Modifier.fillMaxSize(), // Ocupa toda la pantalla
        // verticalArrangement = Arrangement.Center, // Centra verticalmente dentro de Column
        horizontalAlignment = Alignment.CenterHorizontally
    ) { // Centra horizontalmente
        // LOGO SIMPSONS
        Text("NavegacionPersonajes", fontSize = 24.sp, fontWeight = Bold)

        LazyColumn {
            items(character) { character ->
                CharacterItem(character)
            }
        }
    }
}

@Composable
fun CharacterItem(character: Character) {
    val context = LocalContext.current

    val imageResId = context.resources.getIdentifier(
        character.imagen?.lowercase(),
        "drawable",
        context.packageName
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = character.nombre,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = character.nombre, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = character.genero.toString(), fontSize = 16.sp)
            Text(text = character.esFavorito.toString(), fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun CharactersScreenPreview() {
    Column {
        CharactersScreen(navigateToFilterCharacters = {}, navigateToFavoriteCharacters ={}, navigationArrowBack = {})
    }
}