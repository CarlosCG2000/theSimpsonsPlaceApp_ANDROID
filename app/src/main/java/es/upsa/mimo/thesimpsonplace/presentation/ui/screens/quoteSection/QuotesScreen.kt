package es.upsa.mimo.thesimpsonplace.presentation.ui.screens.quoteSection

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import es.upsa.mimo.thesimpsonplace.domain.entities.Character
import es.upsa.mimo.thesimpsonplace.domain.entities.Quote
import es.upsa.mimo.thesimpsonplace.presentation.ui.components.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.screens.characterSection.CharacterItem
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersList.ListCharactersStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersList.ListCharactersViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesList.ListQuotesStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesList.ListQuotesViewModel

@Composable
fun QuotesScreen(
    viewModel: ListQuotesViewModel = hiltViewModel(),
    navigateToFilterQuotes: () -> Unit,
    navigateToFavoriteQuotes: () -> Unit,
    navigateToGameQuotes: () -> Unit,
    navigationArrowBack:() -> Unit
) {

    val state: State<ListQuotesStateUI> = viewModel.stateQuotes.collectAsState() // sincrono para manejarlo en la UI
    var generateNewQuotes by remember { mutableStateOf(true) }

    //Queremos que siempre que se ejecute mi vista queremos que se ejecute el caso de uso de `queryContacts()` del View Model.
    LaunchedEffect(generateNewQuotes /**Se ejecute el metodo cuando se modifique lo que tengamos aqui (variables), si tenemos 'Unit' se modificar solo una vez */) {
        viewModel.getQuotes()
    }

    Scaffold(
        bottomBar = {
            BottomBarQuoteComponent(
                selectedBarButtom = 1,
                navigateToQuotes = { },
                navigateToFiltersQuotes = navigateToFilterQuotes,
                navigateToFavoritesQuotes = navigateToFavoriteQuotes,
                navigateToGameQuotes = navigateToGameQuotes
            )
        },
        topBar = {
            TopBarComponent(
                title = "Listado de Citas",
                onNavigationArrowBack = navigationArrowBack
            )
        }

    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Green),
            contentAlignment = Alignment.Center
        ) {
                if (state.value.isLoading) {
                        CircularProgressIndicator(
                            color = Color.Yellow // ✅ Cambia el color del spinner a amarillo
                        )
                } else {
                    generateNewQuotes = listQuotes(state.value.quotes, generateNewQuotes)
                }
            }
        }
    }

@Composable
fun listQuotes(quotes: List<Quote>,
               generateNewQuotes: Boolean): Boolean {

    var changeQuotes by remember { mutableStateOf(generateNewQuotes) }

    Column(
        modifier = Modifier.fillMaxSize(), // Ocupa toda la pantalla
        verticalArrangement = Arrangement.Center, // Centra verticalmente dentro de Column
        horizontalAlignment = Alignment.CenterHorizontally
    ) { // Centra horizontalmente
        // LOGO SIMPSONS
        Button(onClick = { changeQuotes = !generateNewQuotes }) {
            Text("Nuevos citas")
        }

        LazyColumn {
            items(quotes) { quote ->
                QuoteItem(quote)
            }
        }
    }

    return changeQuotes
}


@Composable
fun QuoteItem(quote: Quote,
              /** dbQuoteViewModel: DbQuotesViewModel = hiltViewModel() */
              ) {

    // Crear boton de estrella, (cada vez que se de se cambia de valor), modificar de estado en la BD
    var isFavorite by remember { mutableStateOf(quote.esFavorito) }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2C3E72)), // Azul oscuro
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(0.5f)
                ) {
                    Text(
                        text = quote.cita,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = quote.personaje,
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    IconButton(onClick = {
                        isFavorite = !isFavorite
                        // funcion de cambio en la BD
                        /** if (isFavorite){
                            dbQuoteViewModel.insert(quote)
                        } else {
                            dbQuoteViewModel.delete(quote)
                        } */
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Star, // Usa el ícono de estrella
                            contentDescription = "Favorito",
                            tint = if (isFavorite) Color.Yellow else Color.Red, // Amarillo si es favorito, rojo si no
                            modifier = Modifier.size(38.dp) // Tamaño del icono
                        )
                    }
                }

                Log.i("image","${quote.imagen.toString()}")
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(quote.imagen.toString())
                        .crossfade(true)
                        .build(),
                    contentDescription = "Character Image",
                    modifier = Modifier
                        .size(150.dp),
                    contentScale = ContentScale.Fit
                )
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