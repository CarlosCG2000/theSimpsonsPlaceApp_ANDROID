package es.upsa.mimo.thesimpsonplace.presentation.ui.screens.quoteSection

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.presentation.ui.components.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesList.ListQuotesStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesList.ListQuotesViewModel

@Composable
fun QuotesFilterScreen(
    viewModel: ListQuotesViewModel = hiltViewModel(),
    navigateToQuotes: () -> Unit,
    navigateToFavoriteQuotes: () -> Unit,
    navigateToGameQuotes: () -> Unit,
    navigationArrowBack:() -> Unit
) {

    val state: State<ListQuotesStateUI> = viewModel.stateQuotes.collectAsState() // sincrono para manejarlo en la UI

    //Queremos que siempre que se ejecute mi vista queremos que se ejecute el caso de uso de `queryContacts()` del View Model.
    LaunchedEffect(Unit /**Se ejecute el metodo cuando se modifique lo que tengamos aqui (variables), si tenemos 'Unit' se modificar solo una vez */) {
        viewModel.getQuotes()
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Magenta),
            contentAlignment = Alignment.Center
        ) {



            if (state.value.isLoading) {
                CircularProgressIndicator(
                    color = Color.Yellow // ✅ Cambia el color del spinner a amarillo
                )
            } else {
                listQuotes(quotes = state.value.quotes)
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