package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.quoteSection

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.R
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.ModifierContainer
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.NoContentComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.quote.BottomBarQuoteComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.quote.BottomNavQuotesItem
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.quote.ListQuotes
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
        Box(
            modifier = ModifierContainer(paddingValues),
            contentAlignment = Alignment.Center // 🔹 Centra el contenido
        ) {
            if (stateFav.value.isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else if (stateFav.value.quotes.isEmpty()) {
                NoContentComponent(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary),
                    titleText = stringResource(R.string.no_existen_citas),
                    infoText = stringResource(R.string.no_existen_citas_favoritas)
                )
            } else {
                ListQuotes(
                    modifier = Modifier.fillMaxSize(),
                    quotes = stateFav.value.quotes,
                    favoriteQuotes = stateFav.value.quotesSet, // saber que citas son favoritas
                    onToggleFavorite = { quote -> viewModel.toggleFavorite(quote) }
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun QuotesFavScreenPreview() {
    QuotesFavScreen(navigateToQuotes = {},
        navigateToFilterQuotes = {},
        navigateToGameQuotes = {},
        navigationArrowBack = {})
}
