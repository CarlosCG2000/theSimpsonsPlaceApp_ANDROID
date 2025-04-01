package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.characterSection

import android.content.res.Configuration
import androidx.compose.foundation.Image
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import es.upsa.mimo.thesimpsonplace.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
    val state: State<ListCharactersStateUI> = viewModel.stateCharacter.collectAsState() // sincrono para manejarlo en la UI
    val stateFav: State<ListCharactersDbStateUI> = viewModelDB.stateCharacterFav.collectAsState()

    //Queremos que siempre que se ejecute mi vista queremos que se ejecute el caso de uso de `queryContacts()` del View Model.
    LaunchedEffect(Unit /*Se ejecute el metodo cuando se modifique lo que tengamos aqui (variables), si tenemos 'Unit' se modificar solo una vez */) {
        viewModel.getAllCharacters()
    }

    Scaffold(
        bottomBar = {
            BottomBarComponent(
                BottomNavItem.ALL,
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
                // .background(Color.Gray) // ✅ Fondo blanco para mejor visibilidad,
        ) {
            if(state.value.isLoading){
                CircularProgressIndicator(
                    color = Color.Yellow // ✅ Cambia el color del spinner a amarillo
                )
            }
            else {
                CharacterList(
                    modifier = Modifier.fillMaxSize(),
                    characters = state.value.characters, // se muestran todos los personajes (indepen de que sean de la BD o no)
                    favoriteCharacters = stateFav.value.charactersSet, // saber que personajes son favoritos
                    onToggleFavorite = { character -> viewModelDB.toggleFavorite(character) })
            }
        }
    }
}

@Composable
fun CharacterList(modifier: Modifier = Modifier,
                  characters: List<Character>,
                  favoriteCharacters: Set<Int>,
                  onToggleFavorite: (Character) -> Unit) {

    // val characters by characterDao.getAllCharactersDb().collectAsState(emptyList<CharacterDb>())
    LazyColumn( modifier = modifier,  horizontalAlignment = Alignment.CenterHorizontally) {
        items(characters) { character ->

            val isFavorite = rememberUpdatedState(character.id in favoriteCharacters)

            CharacterItem(
                character = character,
                isFavorite = isFavorite.value,
                onToggleFavorite = {
                    onToggleFavorite(character)
                })
        }
    }
}

@Composable
fun CharacterItem( character: Character,
                   isFavorite: Boolean,
                   onToggleFavorite: () -> Unit) {
    val context = LocalContext.current

    val imageResId = remember(character.imagen) {
        val id = context.resources.getIdentifier(
            character.imagen?.lowercase(),
            "drawable",
            context.packageName
        )
        if (id == 0) R.drawable.not_specified else id
    }

    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = character.nombre,
            modifier = Modifier.size(80.dp).clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = character.nombre, fontWeight = Bold, fontSize = 20.sp)
            Text(text = character.genero.toString(), fontSize = 16.sp)

            IconButton(onClick = { onToggleFavorite() }) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Favorito",
                    tint = if (isFavorite) Color.Yellow else Color.Gray,
                    modifier = Modifier.size(38.dp)
                )
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