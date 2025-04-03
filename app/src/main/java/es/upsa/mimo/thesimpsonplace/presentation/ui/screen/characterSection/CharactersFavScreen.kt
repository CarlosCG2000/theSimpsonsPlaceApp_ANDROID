package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.characterSection

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.R
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.BottomBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.BottomNavItem
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.ModifierContainer
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.NoContentComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.character.CharacterList
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersListFav.ListCharactersDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersListFav.ListCharactersDbStateUI

@Composable
fun CharactersFavScreen(viewModel: ListCharactersDBViewModel = hiltViewModel(),
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
            ) { /** es esta pantalla, no necesita navegar */ }
        },
        topBar = {
            TopBarComponent(
                title = stringResource(R.string.personajes_favoritos),
                onNavigationArrowBack = navigationArrowBack
            )
        }
    ) { paddingValues ->
        Box(
            contentAlignment = Alignment.Center,
            modifier =  ModifierContainer(paddingValues)
        ) {
            if (stateFav.value.isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else if (stateFav.value.characters.isEmpty()) {
                NoContentComponent(
                    modifier = Modifier.fillMaxSize()
                                        .background(MaterialTheme.colorScheme.primary),
                    titleText = stringResource(R.string.titulo_no_contenido_filtro_pers),
                    infoText = stringResource(R.string.detalles_no_contenido_fav_pers)
                )
            } else {
                CharacterList(
                    modifier = Modifier.fillMaxSize()
                                        .background(MaterialTheme.colorScheme.primary),
                    characters = stateFav.value.characters, // todos los personajes en la Base datos (ya van a ser favoritos)
                    favoriteCharacters = stateFav.value.charactersSet,
                    onToggleFavorite = { character -> viewModel.toggleFavorite(character) }
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun CharactersFavScreenPreview() {
    Column {
        CharactersFavScreen(navigateToAllCharacters = {},
                            navigateToFilterCharacters = {},
                            navigationArrowBack = {})
    }
}

