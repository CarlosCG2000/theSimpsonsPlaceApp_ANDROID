package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.quoteSection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.R
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.quote.BottomBarQuoteComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.quote.BottomNavQuotesItem
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
                selectedBarButtom = BottomNavQuotesItem.FAVORITES,
                navigateToQuotes = navigateToQuotes,
                navigateToFiltersQuotes = navigateToFilterQuotes,
                navigateToFavoritesQuotes = { /** es esta pantalla, no necesita navegar */ },
                navigateToGameQuotes = navigateToGameQuotes
            )
        },
        topBar = {
            TopBarComponent(
                title = stringResource(R.string.citas_favoritos),
                onNavigationArrowBack = navigationArrowBack
            )
        }
    ) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
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
                contentAlignment = Alignment.Center // 🔹 Centra el contenido
            ) {
                if (stateFav.value.isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
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