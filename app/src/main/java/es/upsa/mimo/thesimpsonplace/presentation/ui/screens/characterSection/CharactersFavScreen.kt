package es.upsa.mimo.thesimpsonplace.presentation.ui.screens.characterSection

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.data.mappers.toCharacter
import es.upsa.mimo.thesimpsonplace.presentation.ui.components.BottomBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.components.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.ListCharactersDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersFilterName.ListCharactersFilterViewModel
import kotlin.Int

@Composable
fun CharactersFavScreen( viewModel: ListCharactersDBViewModel = hiltViewModel(),
                        navigateToAllCharacters: () -> Unit,
                        navigateToFilterCharacters: () -> Unit,
                        navigationArrowBack:() -> Unit
) {
    val favorites = viewModel.favoriteCharacters.collectAsState()
    val characters = viewModel.characters.collectAsState()

    Scaffold(
        bottomBar = {
            BottomBarComponent(
                3,
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

            if (characters.value.isEmpty()) {
                Text(text = "No tienes personajes favoritos", fontSize = 20.sp)
            } else {
                CharacterList(
                    modifier = Modifier.fillMaxSize(),
                    characters = characters.value,
                    favoriteCharacters = favorites.value,
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