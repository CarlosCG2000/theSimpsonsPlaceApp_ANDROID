package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.characterSection

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.BottomBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersList.ListCharactersViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersList.ListCharactersStateUI
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import es.upsa.mimo.thesimpsonplace.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.models.Character
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.BottomNavItem
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersListFav.ListCharactersDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersListFav.ListCharactersDbStateUI

@Composable
fun CharactersScreen (
    viewModel: ListCharactersViewModel = hiltViewModel(), // = viewModel(factory = ListCharactersViewModel.factory()
    viewModelDB: ListCharactersDBViewModel = hiltViewModel(),
    navigateToFilterCharacters: () -> Unit,
    navigateToFavoriteCharacters: () -> Unit,
    navigationArrowBack:() -> Unit
) {
    val state: State<ListCharactersStateUI> = viewModel.stateCharacter.collectAsState() // sincrono para manejarlo en la UI, todos los personajes
    val stateFav: State<ListCharactersDbStateUI> = viewModelDB.stateCharacterFav.collectAsState() // sincrono para manejarlo en la UI, los personajes favoritos

    // Queremos que siempre que se ejecute mi vista queremos que se ejecute el caso de uso de `queryContacts()` del View Model.
    LaunchedEffect(Unit) {
        // ✅ ¿Para que sirve 'LaunchedEffect(Unit)'? LaunchedEffect se usa para ejecutar código dentro de Composable de forma segura.
        /**
        🔹 Explicación de Unit
        • Cuando Unit se usa como clave, significa que el código dentro de LaunchedEffect solo se ejecutará una vez cuando el Composable se monte.
        • Si la clave cambia, LaunchedEffect se vuelve a ejecutar (por ejemplo, si pasas un id).
        ¿Porque me has recomendado añadir el 'if (state.value.characters.isEmpty()) {  }' dentro si se supone que al ser 'Unit' solo se va a renderidar una unica vez?
        Porque aunque 'LaunchedEffect(Unit)' solo se ejecuta una vez, el ViewModel sobrevive a recomposiciones y puede contener datos previos.
        'LaunchedEffect(Unit)' solo se ejecuta una vez cuando el Composable se monta, pero el estado (state.value.characters) puede actualizarse varias veces después debido a recomposiciones.
        ¿Por qué agregar if (state.value.characters.isEmpty())? El problema es que si getAllCharacters() ya se ejecutó antes y los datos están en caché o en la BD, podrías estar llamando a la API innecesariamente.
        📌 Ejemplo real: Pantalla de lista de personajes
        1. El usuario abre la pantalla → getAllCharacters() se ejecuta y carga la lista.
        2. El usuario navega a otra pantalla y vuelve → LaunchedEffect(Unit) se ejecuta nuevamente.
        • Si no usamos el if, se vuelve a llamar a getAllCharacters(), aunque la lista ya estaba cargada.
        • Si usamos el if, la llamada solo se hace si la lista está vacía, evitando carga innecesaria.
         */
        if (state.value.characters.isEmpty()) { // Llamar solo si el estado inicial está vacío.
            viewModel.getAllCharacters()
        }
    }

    Scaffold(
        bottomBar = {
            BottomBarComponent(
                BottomNavItem.ALL,
                { /** es esta pantalla, no necesita navegar */},
                navigateToFilterCharacters,
                navigateToFavoriteCharacters
            )
        },
        topBar = {
            TopBarComponent(
                title = stringResource(R.string.todos_los_personajes),
                onNavigationArrowBack = navigationArrowBack
            )
        }
    ) { paddingValues ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            if(state.value.isLoading){
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary // ✅ ¿Como le pongo el color de mi tema 'TheSimpsonPlaceTheme' de la variable 'onPrimary'?
                )
            }
            else {
                CharacterList(
                    modifier = Modifier.fillMaxSize(),
                    characters = state.value.characters, // se muestran todos los personajes (independiente de que sean de la BD o no, se obtienen del Json)
                    favoriteCharacters = stateFav.value.charactersSet, // saber que personajes son favoritos
                    onToggleFavorite = { character -> viewModelDB.toggleFavorite(character) }) // acción de actualizar personajes (a favorito o no) en la base de datos
            }
        }
    }
}

@Composable
fun CharacterList(modifier: Modifier = Modifier,
                  characters: List<Character>,
                  favoriteCharacters: Set<Int>,
                  onToggleFavorite: (Character) -> Unit) {

    LazyColumn( modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally) {
        items(characters) { character ->

            // val isFavorite = rememberUpdatedState(character.id in favoriteCharacters) // ✅ ¿que es 'rememberUpdatedState' y porque este tipo y no otro?
            /**
            •	rememberUpdatedState(value) mantiene actualizado un valor que cambia frecuentemente, evitando recomposiciones innecesarias.
            •	En este caso:
            •	character.id in favoriteCharacters puede cambiar cuando se agregan/eliminan favoritos.
            •	rememberUpdatedState garantiza que CharacterItem siempre tenga el valor más reciente sin recomposiciones extra.
            - Alternativa sin rememberUpdatedState (puede recomponer más de la cuenta)
             */
            val isFavorite = character.id in favoriteCharacters

            CharacterItem(
                character = character,
                isFavorite = isFavorite/*.value*/,
                onToggleFavorite = { onToggleFavorite(character) }
            )
        }
    }
}

@Composable
fun CharacterItem( character: Character,
                   isFavorite: Boolean,
                   onToggleFavorite: () -> Unit) {

    val context = LocalContext.current

    val imageResId = remember(character.imagen) {
        val id = context.resources.getIdentifier( // ⚠️ getIdentifier, esta deprecado pero aún funciona y sigue siendo la única opción dinámica.
            character.imagen?.lowercase(),
            "drawable",
            context.packageName
        )
        if (id == 0) R.drawable.not_specified else id
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C3E72)), //if (isFavorite) Color.Gray else Color(0xFF2C3E72) )
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
    {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = character.nombre,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = character.nombre, fontWeight = Bold, fontSize = 20.sp)
                Text(text = character.genero.toString(), fontSize = 16.sp)

                IconButton(onClick = { onToggleFavorite() }) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = stringResource(R.string.favorito),
                        tint = if (isFavorite) Color.Yellow else Color.Gray,
                        modifier = Modifier.size(38.dp)
                    )
                }
            }
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