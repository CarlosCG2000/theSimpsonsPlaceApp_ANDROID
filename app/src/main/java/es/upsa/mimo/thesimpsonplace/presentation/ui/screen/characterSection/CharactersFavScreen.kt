package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.characterSection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.BottomBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.BottomNavItem
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersListFav.ListCharactersDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersListFav.ListCharactersDbStateUI

@Composable
fun CharactersFavScreen( viewModel: ListCharactersDBViewModel = hiltViewModel(),
                        navigateToAllCharacters: () -> Unit,
                        navigateToFilterCharacters: () -> Unit,
                        navigationArrowBack:() -> Unit
) {
    val stateFav: State<ListCharactersDbStateUI> = viewModel.stateCharacterFav.collectAsState()

    Scaffold(
        bottomBar = {
            BottomBarComponent(
                BottomNavItem.FAVORITES,
                navigateToAllCharacters,
                navigateToFilterCharacters
            ) { }
        }, topBar = {
            TopBarComponent(
                title = "Listado de Personajes Fav",
                onNavigationArrowBack = navigationArrowBack
            )
        }
    ) { paddingValues ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            if (stateFav.value.characters.isEmpty()) {
                Text(text = "No tienes personajes favoritos", fontSize = 20.sp)
            } else {
                CharacterList(
                    modifier = Modifier.fillMaxSize(),
                    characters = stateFav.value.characters, // todos los personajes en la Base datos (ya van a ser favoritos)
                    favoriteCharacters = stateFav.value.charactersSet,
                    onToggleFavorite = { character -> viewModel.toggleFavorite(character) }
                )
            }
        }
    }
}

//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
//@Composable
//fun CharactersFavScreenPreview() {
//    Column {
//        CharactersFavScreen({},{}, {})
//    }
//}