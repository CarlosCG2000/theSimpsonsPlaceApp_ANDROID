package es.upsa.mimo.thesimpsonplace.presentation.ui.screens.characterSection

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewModelScope
import es.upsa.mimo.thesimpsonplace.presentation.ui.components.BottomBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.components.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersFilterName.ListCharactersFilterStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersFilterName.ListCharactersFilterViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersList.ListCharactersStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersList.ListCharactersViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CharacterFilterScreen(viewModel: ListCharactersFilterViewModel = hiltViewModel(),
                          navigateToAllCharacters: () -> Unit,
                          navigateToFavoriteCharacters: () -> Unit,
                          navigationArrowBack:() -> Unit
) {

    val state: State<ListCharactersFilterStateUI> = viewModel.stateCharacterFilter.collectAsState() // sincrono para manejarlo en la UI

    var filtroNombre by remember { mutableStateOf(TextFieldValue("")) } // Estado del campo usuario
    var debounceJob by remember { mutableStateOf<Job?>(null) } // Para cancelar el debounce

    LaunchedEffect(Unit/*Se ejecute el metodo cuando se modifique lo que tengamos aqui (variables), si tenemos 'Unit' se modificar solo una vez */) {
        viewModel.getFilterNameCharacters(filtroNombre.text)
    }

    Scaffold(
        bottomBar = {
            BottomBarComponent(
                2,
                navigateToAllCharacters,
                { },
                navigateToFavoriteCharacters
            )
        },
        topBar = {
            TopBarComponent(
                title = "Listado de Personajes Fav",
                onNavigationArrowBack = navigationArrowBack
            )
        }
    )
    { paddingValues ->
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
                                .padding(paddingValues)
                                // .background(Color.Gray)
        ){

            val (textFieldFilter, characterList) = createRefs()

            TextField(
                value = filtroNombre,
                onValueChange = { newValue ->
                    filtroNombre = newValue
                        /*.copy(
                        selection = TextRange(newValue.text.length) // Mantiene el cursor al final
                        )*/
                    debounceJob?.cancel() // Cancelamos la tarea anterior si hay una nueva entrada
                    debounceJob = viewModel.viewModelScope.launch {
                        delay(350) // Esperamos 500 ms antes de ejecutar el filtro
                        viewModel.getFilterNameCharacters(filtroNombre.text)
                } },
                label = { Text("Nombre del personaje") },
                placeholder = {Text("Homer, Smithers, Milhouse...")},
                trailingIcon = {
                    if (filtroNombre.text.isNotEmpty()) {
                        IconButton(onClick = {
                            filtroNombre = TextFieldValue("")
                            viewModel.getFilterNameCharacters(filtroNombre.text)
                        }) {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "Borrar texto")
                        }
                    }
                },
                modifier = Modifier
                    .constrainAs(textFieldFilter){
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            )

            if(state.value.isLoading){
                CircularProgressIndicator(
                    color = Color.Yellow,
                    modifier = Modifier.constrainAs(characterList) {
                        centerTo(parent)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                )
            } else {
                CharacterList(
                    Modifier.constrainAs(characterList) {
                        top.linkTo(textFieldFilter.bottom)
                        start.linkTo(parent.start)
                    },
                    state.value.characters
                )
            }
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
