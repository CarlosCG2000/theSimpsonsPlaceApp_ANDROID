package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.quoteSection

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.R
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.ModifierContainer
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.quote.BottomBarQuoteComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.quote.BottomNavQuotesItem
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.quote.ListQuotes
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesList.ListQuotesStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesList.ListQuotesViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesListFav.ListQuotesDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesListFav.ListQuotesDbStateUI

@Composable
fun QuotesScreen(
    viewModel: ListQuotesViewModel = hiltViewModel(),
    viewModelDB: ListQuotesDBViewModel = hiltViewModel(),
    navigateToFilterQuotes: () -> Unit,
    navigateToFavoriteQuotes: () -> Unit,
    navigateToGameQuotes: () -> Unit,
    navigationArrowBack:() -> Unit
) {

    val state: State<ListQuotesStateUI> = viewModel.stateQuotes.collectAsState() // sincrono para manejarlo en la UI
    val stateFav: State<ListQuotesDbStateUI> = viewModelDB.stateQuotesFav.collectAsState()

    var generateNewQuotes by remember { mutableStateOf(true) }

    LaunchedEffect(generateNewQuotes /**Se ejecute el metodo cuando se modifique lo que tengamos aqui (variables), si tenemos 'Unit' se modificar solo una vez */) {
        viewModel.getQuotes()
    }

    Scaffold(
        bottomBar = {
            BottomBarQuoteComponent(
                selectedBarButtom = BottomNavQuotesItem.MAIN,
                navigateToQuotes = { /** es esta pantalla, no necesita navegar */ },
                navigateToFiltersQuotes = navigateToFilterQuotes,
                navigateToFavoritesQuotes = navigateToFavoriteQuotes,
                navigateToGameQuotes = navigateToGameQuotes
            )
        },
        topBar = {
            TopBarComponent(
                title = stringResource(R.string.citas_aleatorias),
                onNavigationArrowBack = navigationArrowBack
            )
        }

    ) { paddingValues ->
        ConstraintLayout(
            modifier = ModifierContainer(paddingValues),
            constraintSet = quotesScreenConstraintSet()
        ) {
            Button(
                onClick = { generateNewQuotes = !generateNewQuotes },
                colors = ButtonDefaults.buttonColors( MaterialTheme.colorScheme.secondary),
                modifier = Modifier.layoutId("idBoton")
                                    .clip(RoundedCornerShape(12.dp)) // Bordes redondeados
            ) {
                Text(
                    text = "Nuevas Citas",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                )
            }

            // Contenido centrado en el resto de la pantalla
            Box(
                modifier = Modifier.layoutId("idListado"),
                contentAlignment = Alignment.Center, // 🔹 Centra el contenido
            ) {
                if (state.value.isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    ListQuotes(
                        quotes = state.value.quotes,
                        favoriteQuotes = stateFav.value.quotesSet, // saber que citas son favoritas
                        onToggleFavorite = { quote -> viewModelDB.toggleFavorite(quote) }
                    )
                }
            }

        }
    }
}

fun quotesScreenConstraintSet(): ConstraintSet {
    return ConstraintSet {
        val (boton, listado) = createRefsFor("idBoton", "idListado")

        constrain(boton) {
            top.linkTo(parent.top, margin = 10.dp)
            bottom.linkTo(listado.top, margin = 10.dp) // 🔹 Margen inferior
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            // width = Dimension.value(30.dp)
        }

        constrain(listado) {
            top.linkTo(boton.bottom)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.fillToConstraints
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun QuoteScreenPreview() {
    QuotesScreen(
        navigateToFilterQuotes = {},
        navigateToFavoriteQuotes = {},
        navigateToGameQuotes = {},
        navigationArrowBack = {})
}