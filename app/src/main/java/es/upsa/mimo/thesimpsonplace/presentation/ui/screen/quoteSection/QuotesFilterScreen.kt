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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.R
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.ModifierContainer
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.MySearchTextField
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.NoContentComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.quote.BottomBarQuoteComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.quote.BottomNavQuotesItem
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.quote.ListQuotes
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesList.ListQuotesStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesList.ListQuotesViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesListFav.ListQuotesDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesListFav.ListQuotesDbStateUI

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

    var selectedItem by remember { mutableIntStateOf(3) }
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
            modifier = ModifierContainer(paddingValues),
            constraintSet = quotesFilterScreenConstraintSet()
        ) {

            Box(
                modifier = Modifier
                    .layoutId("idTextfield")
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(6.dp)
            ) {
                MySearchTextField(nameFilter = filterName,
                                  valueChange = { newValue -> filterName = newValue })
            }

            SegmentedPicker(
                options = options,
                selectedOption = selectedItem,
                onOptionSelected = { selectedItem = it },
                modifier = Modifier
                    .layoutId("idSegmentedPicker")
                    .fillMaxWidth()
                    //.padding(horizontal = 8.dp)
                    .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(12.dp))
            )

            // Contenido centrado en el resto de la pantalla
            if (state.value.isLoading) {
                Box(
                    modifier = Modifier.layoutId("idListado"),
                    contentAlignment = Alignment.Center // 🔹 Centra el contenido
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                }
            } else if (state.value.quotes.isEmpty()) {
                NoContentComponent(
                    modifier = Modifier.layoutId("idListado"),
                    titleText = stringResource(R.string.no_existen_citas),
                    infoText = stringResource(R.string.desc_no_existen_citas)
                )
            } else {
                ListQuotes(
                    modifier = Modifier.layoutId("idListado"),
                    quotes = state.value.quotes,
                    favoriteQuotes = stateFav.value.quotesSet, // saber que citas son favoritas
                    onToggleFavorite = { quote -> viewModelDB.toggleFavorite(quote) }
                )
            }
        }
    }
}

fun quotesFilterScreenConstraintSet(): ConstraintSet {
    return ConstraintSet {

        val (textfield, segmentedPicker, listado) =  createRefsFor("idTextfield", "idSegmentedPicker", "idListado")

        constrain(textfield) {
            top.linkTo(parent.top, margin = 10.dp)
            bottom.linkTo(segmentedPicker.top) // 🔹 Margen inferior
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }

        constrain(segmentedPicker) {
            top.linkTo(textfield.bottom)
            bottom.linkTo(listado.top) // 🔹 Margen inferior
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(listado) {
            top.linkTo(segmentedPicker.bottom)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.fillToConstraints
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
                //modifier = Modifier.padding(4.dp)
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