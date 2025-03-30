package es.upsa.mimo.thesimpsonplace.presentation.ui.screens.quoteSection

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import es.upsa.mimo.thesimpsonplace.presentation.ui.components.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersListFav.ListCharactersDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersListFav.ListCharactersDbStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesList.ListQuotesStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesList.ListQuotesViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesListFav.ListQuotesDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesListFav.ListQuotesDbStateUI
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun QuotesFilterScreen(
    viewModel: ListQuotesViewModel = hiltViewModel(),
    viewModelDB: ListQuotesDBViewModel = hiltViewModel(),
    navigateToQuotes: () -> Unit,
    navigateToFavoriteQuotes: () -> Unit,
    navigateToGameQuotes: () -> Unit,
    navigationArrowBack:() -> Unit
) {

    val state: State<ListQuotesStateUI> = viewModel.stateQuotes.collectAsState() // sincrono para manejarlo en la UI

    val stateFav: State<ListQuotesDbStateUI> = viewModelDB.stateQuotesFav.collectAsState()

    var filterName by remember { mutableStateOf(TextFieldValue("")) } // Estado del campo usuario
    var debounceJob by remember { mutableStateOf<Job?>(null) } // Para cancelar el debounce

    var selectedItem by remember { mutableStateOf(3) }
    val options = listOf(1, 3, 5, 10)

    //Queremos que siempre que se ejecute mi vista queremos que se ejecute el caso de uso de `queryContacts()` del View Model.
    LaunchedEffect(Unit /**Se ejecute el metodo cuando se modifique lo que tengamos aqui (variables), si tenemos 'Unit' se modificar solo una vez */) {
        viewModel.getQuotes(selectedItem, filterName.text)
    }

    Scaffold(
        bottomBar = {
            BottomBarQuoteComponent(
                selectedBarButtom = 2,
                navigateToQuotes = navigateToQuotes,
                navigateToFiltersQuotes = { },
                navigateToFavoritesQuotes = navigateToFavoriteQuotes,
                navigateToGameQuotes = navigateToGameQuotes
            )
        },
        topBar = {
            TopBarComponent(
                title = "Listado de Citas Filtrado",
                onNavigationArrowBack = navigationArrowBack
            )
        }
    ) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)
        ) {
            val (textfield, segmentedPicker, listado) = createRefs()

            TextField(
                value = filterName,
                onValueChange = { newValue ->
                    filterName = newValue
                    debounceJob?.cancel() // Cancelamos la tarea anterior si hay una nueva entrada
                    debounceJob = viewModel.viewModelScope.launch {
                        delay(350) // Esperamos 500 ms antes de ejecutar el filtro
                        viewModel.getQuotes(numElementos = selectedItem, textPersonaje = newValue.text)
                    }
                },
                label = { Text("Nombre del personaje") },
                placeholder = { Text("Homer, Smithers, Milhouse...")},
                trailingIcon = {
                    if (filterName.text.isNotEmpty()) {
                        IconButton(onClick = {
                            filterName = TextFieldValue("")
                            // logicaFiltrado(title = "")
                        }) {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "Borrar texto")
                        }
                    }
                },
                modifier = Modifier.constrainAs(textfield) {
                    top.linkTo(parent.top, margin = 10.dp)
                    bottom.linkTo(segmentedPicker.top) // 🔹 Margen inferior
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .padding(horizontal = 10.dp, vertical = 5.dp)
            )

            SegmentedPicker(
                options = options,
                selectedOption = selectedItem,
                onOptionSelected = {
                    selectedItem = it
                    viewModel.getQuotes(numElementos = selectedItem, textPersonaje = filterName.text)
                                   },
                modifier = Modifier.constrainAs(segmentedPicker) {
                    top.linkTo(textfield.bottom)
                    bottom.linkTo(listado.top) // 🔹 Margen inferior
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.fillMaxWidth()
                  .padding(horizontal = 10.dp)
                  .background(Color(0xFF0F1A35), RoundedCornerShape(12.dp) )
            )


            // ✅ Contenido centrado en el resto de la pantalla
            Box(
                modifier = Modifier
                    .constrainAs(listado) {
                        top.linkTo(segmentedPicker.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    },
                //.fillMaxSize(), // 🔹 Se asegura de ocupar el espacio disponible
                contentAlignment = Alignment.Center // 🔹 Centra el contenido
            ) {
                if (state.value.isLoading) {
                    CircularProgressIndicator(color = Color.Yellow)
                } else {
                    listQuotes(
                        modifier = Modifier.fillMaxSize(),
                        quotes = state.value.quotes,
                        favoriteQuotes = stateFav.value.quotesSet, // saber que citas son favoritas
                        onToggleFavorite = { quote -> viewModelDB.toggleFavorite(quote) }
                    )
                }
            }

        }
    }
}

@Composable
fun SegmentedPicker(
    modifier: Modifier = Modifier,
    options: List<Int>,
    selectedOption: Int,
    onOptionSelected: (Int) -> Unit
) {
    Row(
        modifier = modifier, // Fondo oscuro y bordes redondeados
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        options.forEach { option ->
            Button(
                onClick = { onOptionSelected(option) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (option == selectedOption) Color.White else Color(0xFF1B2A50), // Fondo blanco si está seleccionado
                    contentColor = if (option == selectedOption) Color.Black else Color.Gray // Texto oscuro si está seleccionado
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(4.dp)
            ) {
                val text = if (option == 1) "Item $option" else "Items $option"
                Text(text)
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun QuotesFilterScreenPreview() {
    QuotesFilterScreen(
        navigateToQuotes = {},
        navigateToFavoriteQuotes = {},
        navigateToGameQuotes = {},
        navigationArrowBack = {})
}