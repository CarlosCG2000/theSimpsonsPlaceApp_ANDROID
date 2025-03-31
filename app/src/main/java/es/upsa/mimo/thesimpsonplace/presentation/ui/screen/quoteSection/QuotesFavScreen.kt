package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.quoteSection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesListFav.ListQuotesDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesListFav.ListQuotesDbStateUI

@Composable
fun QuotesFavScreen(
    viewModel: ListQuotesDBViewModel = hiltViewModel(),
    navigateToQuotes: () -> Unit,
    navigateToFilterQuotes: () -> Unit,
    navigateToGameQuotes: () -> Unit,
    navigationArrowBack:() -> Unit
) {
    val stateFav: State<ListQuotesDbStateUI> = viewModel.stateQuotesFav.collectAsState()

    Scaffold(
        bottomBar = {
            BottomBarQuoteComponent(
                selectedBarButtom = 3,
                navigateToQuotes = navigateToQuotes,
                navigateToFiltersQuotes = navigateToFilterQuotes,
                navigateToFavoritesQuotes = { },
                navigateToGameQuotes = navigateToGameQuotes
            )
        },
        topBar = {
            TopBarComponent(
                title = "Listado de Citas Fav",
                onNavigationArrowBack = navigationArrowBack
            )
        }
    ) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)
        ) {
            val (boton, listado) = createRefs()

            // ✅ Contenido centrado en el resto de la pantalla
            Box(
                modifier = Modifier
                    .constrainAs(listado) {
                        top.linkTo(boton.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    },
                //.fillMaxSize(), // 🔹 Se asegura de ocupar el espacio disponible
                contentAlignment = Alignment.Center // 🔹 Centra el contenido
            ) {
                if (stateFav.value.isLoading) {
                    CircularProgressIndicator(color = Color.Yellow)
                } else {
                    listQuotes(
                        modifier = Modifier.fillMaxSize(),
                        quotes = stateFav.value.quotes,
                        favoriteQuotes = stateFav.value.quotesSet, // saber que citas son favoritas
                        onToggleFavorite = { quote -> viewModel.toggleFavorite(quote) }
                    )
                }
            }

        }
    }
}

//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
//@Composable
//fun QuotesFavScreenPreview() {
//    QuotesFavScreen({}, {}, {}, {})
//}