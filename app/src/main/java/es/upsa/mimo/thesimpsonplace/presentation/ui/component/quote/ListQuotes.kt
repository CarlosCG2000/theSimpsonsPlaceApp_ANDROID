package es.upsa.mimo.thesimpsonplace.presentation.ui.component.quote

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import es.upsa.mimo.thesimpsonplace.domain.models.Quote

@Composable
fun ListQuotes(modifier: Modifier = Modifier,
               quotes: List<Quote>,
               favoriteQuotes: Set<String>,
               onToggleFavorite: (Quote) -> Unit
) {

    LazyColumn( modifier = modifier, // Ocupa toda la pantalla
        verticalArrangement = Arrangement.Top, // Centra verticalmente dentro de Column
        horizontalAlignment = Alignment.CenterHorizontally) {

        items(quotes) { quote ->

            val isFavorite = rememberUpdatedState(quote.cita in favoriteQuotes)

            QuoteItem (  quote = quote,
                isFavorite = isFavorite.value,
                onToggleFavorite = { onToggleFavorite(quote) }
            )
        }
    }
}
