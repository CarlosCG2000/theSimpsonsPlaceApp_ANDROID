package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.quoteSection

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import es.upsa.mimo.thesimpsonplace.R
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.BottomBarQuoteComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.BottomNavQuotesItem
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.TopBarComponent
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

   LaunchedEffect(filterName.text, selectedItem) {
        viewModel.getQuotes(selectedItem, filterName.text)
    }

    Scaffold(
        bottomBar = {
            BottomBarQuoteComponent(
                selectedBarButtom = BottomNavQuotesItem.FILTERS,
                navigateToQuotes = navigateToQuotes,
                navigateToFiltersQuotes = { /** es esta pantalla, no necesita navegar */ },
                navigateToFavoritesQuotes = navigateToFavoriteQuotes,
                navigateToGameQuotes = navigateToGameQuotes
            )
        },
        topBar = {
            TopBarComponent(
                title = stringResource(R.string.citas_filtradas),
                onNavigationArrowBack = navigationArrowBack
            )
        }
    ) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val (textfield, segmentedPicker, listado) = createRefs()

            // _______________ TEXTFIELD _______________
            Box(
                modifier = Modifier
                    .constrainAs(textfield) {
                        top.linkTo(parent.top, margin = 10.dp)
                        bottom.linkTo(segmentedPicker.top) // 🔹 Margen inferior
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .background(
                        MaterialTheme.colorScheme.secondary,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(6.dp)
            ) {
                OutlinedTextField(
                    value = filterName,
                    onValueChange = { newValue ->
                        filterName = newValue
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.nombre_del_personaje),
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.ejemplos_personajes),
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    },
                    trailingIcon = {
                        if (filterName.text.isNotEmpty()) {
                            IconButton(onClick = { filterName = TextFieldValue("") }) {
                                Icon(imageVector = Icons.Filled.Close, contentDescription = "Borrar texto")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth() // 🔹 Para que ocupe todo el ancho del Box
                )
            }
            // _________________________________________

            SegmentedPicker(
                options = options,
                selectedOption = selectedItem,
                onOptionSelected = {
                    selectedItem = it
                    viewModel.getQuotes(numElementos = selectedItem, textPersonaje = filterName.text)
                                   },
                modifier = Modifier
                    .constrainAs(segmentedPicker) {
                        top.linkTo(textfield.bottom)
                        bottom.linkTo(listado.top) // 🔹 Margen inferior
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(12.dp))
            )


            // Contenido centrado en el resto de la pantalla
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
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.primary),
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
        modifier = modifier.background(MaterialTheme.colorScheme.primary), // Fondo oscuro y bordes redondeados
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        options.forEach { option ->
            Button(
                onClick = { onOptionSelected(option) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (option == selectedOption) MaterialTheme.colorScheme.onPrimary
                                        else MaterialTheme.colorScheme.secondary, // Fondo blanco si está seleccionado
                    contentColor = if (option == selectedOption) Color.Black
                                    else Color.Black // Texto oscuro si está seleccionado
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(4.dp)
            ) {
                val text = if (option == 1) stringResource(R.string.item, option)
                                            else stringResource(R.string.items, option)
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