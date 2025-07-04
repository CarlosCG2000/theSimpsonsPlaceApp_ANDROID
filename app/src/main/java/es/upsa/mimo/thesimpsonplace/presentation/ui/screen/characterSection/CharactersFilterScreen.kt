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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.constraintlayout.compose.Dimension
import es.upsa.mimo.thesimpsonplace.R
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.BottomBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.BottomNavItem
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.ModifierContainer
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.MySearchTextField
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.NoContentComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.character.CharacterList
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersListFav.ListCharactersDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersFilterName.ListCharactersFilterStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersFilterName.ListCharactersFilterViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersListFav.ListCharactersDbStateUI
import kotlinx.coroutines.delay

@Composable
fun CharacterFilterScreen(viewModel: ListCharactersFilterViewModel = hiltViewModel(),
                          viewModelDB: ListCharactersDBViewModel = hiltViewModel(),
                          navigateToAllCharacters: () -> Unit,
                          navigateToFavoriteCharacters: () -> Unit,
                          navigationArrowBack:() -> Unit
) {

    val state: State<ListCharactersFilterStateUI> = viewModel.stateCharacterFilter.collectAsState() // sincrono para manejarlo en la UI
    val stateFav: State<ListCharactersDbStateUI> = viewModelDB.stateCharacterFav.collectAsState()

    var filtroNombre by remember { mutableStateOf(TextFieldValue("")) } // Estado del campo usuario

    LaunchedEffect( filtroNombre.text) {// cada vez que se cambie el texto de filtro se realiza la búsqueda
        if (filtroNombre.text.isNotEmpty()) // siempre que tengamos texto
            delay(350) // debounce para evita llamadas innecesarias al escribir rápido
       viewModel.getFilterNameCharacters(filtroNombre.text)
    }

    Scaffold(
        bottomBar = {
            BottomBarComponent(
                selectedBarButtom = BottomNavItem.FILTERS,
                navigateToAllEpisodes = navigateToAllCharacters,
                navigateToFiltersEpisode = { /** es esta pantalla, no necesita navegar */ },
                navigateToFavoritesEpisode = navigateToFavoriteCharacters
            )
        },
        topBar = {
            TopBarComponent(
                title = stringResource(R.string.filtro_de_personajes),
                onNavigationArrowBack = navigationArrowBack
            )
        }
    )
    { paddingValues ->
        ConstraintLayout(
            modifier = ModifierContainer(paddingValues),
            constraintSet = characterFilterScreenConstraintSet()
        ){

            Box(
                modifier = Modifier.layoutId("idBoxMySearch")
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.secondary,
                                        shape = MaterialTheme.shapes.small
                                    )
                                    .padding(6.dp)
            ) {
                MySearchTextField(nameFilter = filtroNombre,
                                valueChange = { newValue -> filtroNombre = newValue })
            }

            Box(
                modifier = Modifier.layoutId("idBoxMyCharacterList"),
                contentAlignment = Alignment.Center // 🔹 Centra el contenido
            ) {
                if(state.value.isLoading == true) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                } else if (state.value.characters.isEmpty()) {
                    NoContentComponent(
                        modifier = Modifier.fillMaxSize()
                                            .background(MaterialTheme.colorScheme.primary),
                        titleText = stringResource(R.string.titulo_no_contenido_filtro_pers),
                        infoText = stringResource(R.string.detalles_no_contenido_filtro_pers)
                    )
                } else {
                    CharacterList(
                        modifier = Modifier.fillMaxSize()
                                            .background(MaterialTheme.colorScheme.primary),
                        characters = state.value.characters,
                        favoriteCharacters = stateFav.value.charactersSet, // personajes favoritos
                        onToggleFavorite = { character -> viewModelDB.toggleFavorite(character) })
                }
            }
        }
    }
}

fun characterFilterScreenConstraintSet(): ConstraintSet {
    return ConstraintSet {
        val (textFieldFilter, characterList) = createRefsFor("idBoxMySearch", "idBoxMyCharacterList")

        constrain(textFieldFilter) {
            top.linkTo(parent.top, margin = 10.dp)
            bottom.linkTo(characterList.top, margin = 10.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }

        constrain(characterList) {
            top.linkTo(textFieldFilter.bottom)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.fillToConstraints
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CharacterFilterScreenPreview() {
    Column {
        CharacterFilterScreen(navigateToAllCharacters = {}, navigateToFavoriteCharacters = {}, navigationArrowBack = {})
    }
}
