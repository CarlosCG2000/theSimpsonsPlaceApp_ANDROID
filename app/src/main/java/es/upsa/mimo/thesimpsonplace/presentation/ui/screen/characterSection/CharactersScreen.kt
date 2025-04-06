package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.characterSection

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.BottomBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersList.ListCharactersViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersList.ListCharactersStateUI
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import es.upsa.mimo.thesimpsonplace.R
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.data.entities.character.Gender
import es.upsa.mimo.thesimpsonplace.domain.models.Character
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.BottomNavItem
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.ModifierContainer
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.character.CharacterList
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
        if (state.value.characters.isEmpty())  // Llamar solo si el estado inicial está vacío.
            viewModel.getAllCharacters()
    }

    Scaffold(
        bottomBar = {
            BottomBarComponent(
                selectedBarButtom = BottomNavItem.ALL,
                navigateToAllEpisodes = { /** es esta pantalla, no necesita navegar */ },
                navigateToFiltersEpisode = navigateToFilterCharacters,
                navigateToFavoritesEpisode = navigateToFavoriteCharacters
            )
        },
        topBar = {
            TopBarComponent(
                title = stringResource(R.string.todos_los_personajes),
                onNavigationArrowBack = navigationArrowBack
            )
        }
    ) { paddingValues ->
        if(state.value.isLoading) {
            Box(
                modifier = ModifierContainer(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary // ✅ ¿Como le pongo el color de mi tema 'TheSimpsonPlaceTheme' de la variable 'onPrimary'?
                )
            }
        }
        else {
            CharacterList(
                modifier = ModifierContainer(paddingValues),
                characters = state.value.characters, // se muestran todos los personajes (independiente de que sean de la BD o no, se obtienen del Json)
                favoriteCharacters = stateFav.value.charactersSet, // saber que personajes son favoritos
                onToggleFavorite = { character -> viewModelDB.toggleFavorite(character) }) // acción de actualizar personajes (a favorito o no) en la base de datos
        }
    }
}

// Crear una versión “pura” para testear y previsualizar la UI.
// Es completamente independiente de ViewModel, Hilt o lógica de negocio.
// Recibe tdo lo que necesita como parámetros: datos (characters, favoriteCharacters), estado (isLoading) y acciones (onToggleFavorite, onLoadCharacters, etc.).
//Así puedes testearlo o previsualizarlo sin necesidad de entorno Android completo.
@Composable
fun CharactersScreenMock(
    // Valores de entrada que se pasan desde el ViewModel
    characters: List<Character>,
    favoriteCharacters: Set<Int>,
    isLoading: Boolean,
    onToggleFavorite: (Character) -> Unit,
    onLoadCharacters: () -> Unit,
    // Navegación
    navigateToFilterCharacters: () -> Unit,
    navigateToFavoriteCharacters: () -> Unit,
    navigationArrowBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        if (characters.isEmpty()) {
            onLoadCharacters()
        }
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomBarComponent(
                selectedBarButtom = BottomNavItem.ALL,
                navigateToAllEpisodes = { },
                navigateToFiltersEpisode = navigateToFilterCharacters,
                navigateToFavoritesEpisode = navigateToFavoriteCharacters
            )
        },
        topBar = {
            TopBarComponent(
                title = stringResource(R.string.todos_los_personajes),
                onNavigationArrowBack = navigationArrowBack,
            )
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = ModifierContainer(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        } else {
            CharacterList(
                modifier = ModifierContainer(paddingValues)
                            .background(MaterialTheme.colorScheme.primary),
                characters = characters,
                favoriteCharacters = favoriteCharacters,
                onToggleFavorite = onToggleFavorite
            )
        }
    }
}

// Creo un Preview para testear
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Modo Oscuro")
@Composable
fun CharactersScreenPreview() {
    // Son datos simulados (mock), lo cual es ideal para ver la UI con contenido realista.
    val dummyCharacters = listOf(
        Character(1, "Homer Simpson", Gender.Male, "Homer_Simpson"),
        Character(2, "Marge Simpson", Gender.Female, "Marge"),
        Character(3, "Carl Carlson", Gender.Male, "Carl_Carlson", true),
    )

    CharactersScreenMock(
        characters = dummyCharacters,
        favoriteCharacters = setOf(1),
        isLoading = false,
        onToggleFavorite = {},
        onLoadCharacters = {},
        navigateToFilterCharacters = {},
        navigateToFavoriteCharacters = {},
        navigationArrowBack = {}
    )
}

